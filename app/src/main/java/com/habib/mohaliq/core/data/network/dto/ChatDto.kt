package com.habib.mohaliq.core.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Data models for the OpenAI-compatible chat format used between the app
 * and the Cloudflare Worker.
 *
 * The Worker forwards the request to Google's Gemini API through its
 * OpenAI-compatible endpoint.
 */

@Serializable
data class ChatRequestDto(
    val deviceId: String,
    val messages: List<ChatMessageDto>,
    val tools: List<ToolDto>? = null
)

@Serializable
data class ChatMessageDto(
    val role: String,
    val content: String? = null,
    @SerialName("tool_calls") val toolCalls: List<ToolCallDto>? = null,
    @SerialName("tool_call_id") val toolCallId: String? = null
)

@Serializable
data class ToolDto(
    val type: String,
    val function: FunctionDto
)

@Serializable
data class FunctionDto(
    val name: String,
    val description: String,
    val parameters: JsonObject
)

@Serializable
data class ToolCallDto(
    val id: String,
    val type: String,
    val function: FunctionCallDto,
    @SerialName("extra_content")
    val extraContent: ExtraContentDto? = null
)

@Serializable
data class ExtraContentDto(
    val google: GoogleExtraContentDto? = null
)

@Serializable
data class GoogleExtraContentDto(
    @SerialName("thought_signature")
    val thoughtSignature: String? = null
)

@Serializable
data class FunctionCallDto(
    val name: String,
    val arguments: String
)

@Serializable
data class ChatResponseDto(
    val choices: List<ChoiceDto> = emptyList(),
    val error: ErrorDto? = null
)

@Serializable
data class ChoiceDto(
    val message: ChatMessageDto,
    @SerialName("finish_reason") val finishReason: String? = null
)

@Serializable
data class ErrorDto(
    val message: String? = null
)