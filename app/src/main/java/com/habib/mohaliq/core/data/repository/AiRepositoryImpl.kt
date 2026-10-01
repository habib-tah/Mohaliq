package com.habib.mohaliq.core.data.repository

import com.habib.mohaliq.core.data.datastore.DeviceIdProvider
import com.habib.mohaliq.core.data.network.api.AiApi
import com.habib.mohaliq.core.data.network.dto.ChatMessageDto
import com.habib.mohaliq.core.data.network.dto.ChatRequestDto
import com.habib.mohaliq.core.data.network.dto.FunctionDto
import com.habib.mohaliq.core.data.network.dto.ToolDto
import com.habib.mohaliq.core.model.ChatRole
import com.habib.mohaliq.core.model.ChatTurn
import com.habib.mohaliq.core.model.PlaceType
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import javax.inject.Inject

private const val SYSTEM_PROMPT = """You are Mohaliq's AI travel assistant, helping users plan trips to Bali using the Mohaliq app.
You have a tool, search_places, that searches the app's real hotel, restaurant, and destination listings.
Use it whenever someone asks for specific recommendations, prices, or availability — never invent place names, prices, or ratings.
For general travel advice (what to pack, best time to visit, itinerary ideas) you can answer directly without the tool.
Keep replies concise, friendly, and focused on travel planning."""

private const val MAX_TOOL_ROUNDS = 3

@Serializable
private data class SearchPlacesArgs(
    val type: String,
    val query: String? = null
)

@Serializable
private data class PlaceSummaryDto(
    val id: String,
    val title: String,
    val location: String,
    val rating: Float,
    val price: Double
)

class AiRepositoryImpl @Inject constructor(
    private val aiApi: AiApi,
    private val placeRepository: PlaceRepository,
    private val deviceIdProvider: DeviceIdProvider,
    private val json: Json
) : AiRepository {

    private val searchPlacesTool = ToolDto(
        type = "function",
        function = FunctionDto(
            name = "search_places",
            description = "Search Mohaliq's real hotel, restaurant, and destination listings.",
            parameters = buildJsonObject {
                put("type", "object")
                putJsonObject("properties") {
                    putJsonObject("type") {
                        put("type", "string")
                        putJsonArray("enum") {
                            add("HOTEL")
                            add("RESTAURANT")
                            add("DESTINATION")
                        }
                        put("description", "Which category to search")
                    }
                    putJsonObject("query") {
                        put("type", "string")
                        put(
                            "description",
                            "Optional keyword to filter by name — omit to list all places of that type"
                        )
                    }
                }
                putJsonArray("required") {
                    add("type")
                }
            }
        )
    )

    override suspend fun sendMessage(history: List<ChatTurn>): Result<String> {

        return try {

            val deviceId = deviceIdProvider.getOrCreate()

            val messages = mutableListOf(
                ChatMessageDto(role = "system", content = SYSTEM_PROMPT)
            )

            messages += history.map {
                ChatMessageDto(
                    role = if (it.role == ChatRole.USER) "user" else "assistant",
                    content = it.content
                )
            }

            repeat(MAX_TOOL_ROUNDS) {

                val response = aiApi.sendChat(
                    ChatRequestDto(
                        deviceId = deviceId,
                        messages = messages,
                        tools = listOf(searchPlacesTool)
                    )
                )

                if (response.error != null) {
                    return Result.failure(Exception(response.error.message ?: "The assistant is unavailable right now."))
                }

                val message = response.choices.firstOrNull()?.message
                    ?: return Result.failure(Exception("No response from the assistant."))

                val toolCalls = message.toolCalls

                if (toolCalls.isNullOrEmpty()) {

                    val content = message.content ?: ""

                    // Defensive check: if the model returns a tool-call attempt as plain
                    // text instead of a structured tool call, do not expose it to the user.
                    if (looksLikeMalformedToolCall(content)) {
                        return Result.failure(
                            Exception("The assistant tried to search but responded in an unexpected format.")
                        )
                    }

                    return Result.success(content)
                }

                // The model wants to search real data — run it and feed the
                // result back so it can write a real answer.
                messages += message

                for (toolCall in toolCalls) {

                    val resultJson = runCatching {
                        executeSearchPlaces(toolCall.function.arguments)
                    }.getOrElse { "Search failed: ${it.message}" }

                    messages += ChatMessageDto(
                        role = "tool",
                        content = resultJson,
                        toolCallId = toolCall.id
                    )

                }

            }

            Result.failure(Exception("The assistant is taking too long to respond — try again."))

        } catch (e: Exception) {
            android.util.Log.e("MohaliqAI", "AI request failed", e)
            Result.failure(e)
        }

    }

    private suspend fun executeSearchPlaces(argumentsJson: String): String {

        val args = json.decodeFromString<SearchPlacesArgs>(argumentsJson)

        val type = runCatching { PlaceType.valueOf(args.type.uppercase()) }
            .getOrElse { return "Invalid type \"${args.type}\" — use HOTEL, RESTAURANT, or DESTINATION." }

        val places = placeRepository.observeByType(type).first()

        val filtered = if (args.query.isNullOrBlank()) {
            places
        } else {
            places.filter { it.title.contains(args.query, ignoreCase = true) }
        }

        if (filtered.isEmpty()) {
            return "No matching places found."
        }

        val summaries = filtered.take(5).map {
            PlaceSummaryDto(
                id = it.id,
                title = it.title,
                location = it.location,
                rating = it.rating,
                price = it.currentPrice
            )
        }

        return json.encodeToString(summaries)

    }

    /**
     * Detects tool-call attempts returned as plain text instead of through
     * the structured tool_calls field.
     */
    private fun looksLikeMalformedToolCall(content: String): Boolean {
        val trimmed = content.trim()
        val markers = listOf(
            "<invoke",
            "function_call",
            "<parameter name=",
            "<tool_call",
            "\"name\": \"search_places\""
        )
        return markers.any { trimmed.contains(it, ignoreCase = true) }
    }

}
