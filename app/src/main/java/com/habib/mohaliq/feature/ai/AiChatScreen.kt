package com.habib.mohaliq.feature.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqChip
import com.habib.mohaliq.core.designsystem.component.MohaliqLoadingIndicator
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.ChatRole
import com.habib.mohaliq.core.model.ChatTurn

private val SUGGESTIONS = listOf(
    "Plan a 3-day trip to Bali",
    "Suggest Islamic restaurants in Bali",
    "Hotels under $150",
    "Family activities",
    "Create today's itinerary"
)

@Composable
fun AiChatScreen(
    uiState: AiChatUiState,
    onAction: (AiChatAction) -> Unit
) {

    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size, uiState.isSending) {
        val lastIndex = uiState.messages.size - 1 + if (uiState.isSending) 1 else 0
        if (lastIndex >= 0) listState.animateScrollToItem(lastIndex)
    }

    MohaliqScaffold(

        topBar = {

            MohaliqTopBar(
                title = "AI Assistant",
                navigationIcon = R.drawable.ic_arrow_left_outline,
                onNavigationClick = { onAction(AiChatAction.BackClicked) }
            )

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(Dimens.Space16),
                verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
            ) {

                if (uiState.messages.isEmpty()) {

                    item {

                        EmptyState(
                            onSuggestionClick = {
                                onAction(AiChatAction.SuggestionClicked(it))
                            }
                        )

                    }

                }

                items(
                    items = uiState.messages,
                    key = { it.id }
                ) { message ->

                    MessageBubble(message)

                }

                if (uiState.isSending) {

                    item {

                        TypingBubble()

                    }

                }

            }

            InputBar(
                value = uiState.inputText,
                canSend = uiState.canSend,
                onValueChange = { onAction(AiChatAction.InputChanged(it)) },
                onSendClick = { onAction(AiChatAction.SendClicked) }
            )

        }

    }

}

@Composable
private fun EmptyState(
    onSuggestionClick: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Dimens.Space24))

        Icon(
            painter = painterResource(R.drawable.ic_magicpen),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(40.dp)
        )

        Spacer(modifier = Modifier.height(Dimens.Space12))

        Text(
            text = "Ask me anything about your trip",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "I can search real hotels, restaurants, and destinations for you.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = Dimens.Space4)
        )

        Spacer(modifier = Modifier.height(Dimens.Space16))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {

            items(SUGGESTIONS) { suggestion ->

                MohaliqChip(
                    text = suggestion,
                    selected = false,
                    onClick = { onSuggestionClick(suggestion) }
                )

            }

        }

    }

}

private val BOLD_REGEX = Regex("\\*\\*(.+?)\\*\\*")

/**
 * Applies lightweight Markdown formatting supported by the chat UI,
 * including bold text and bullet lines.
 */
private fun formatMarkdownLite(text: String): AnnotatedString {

    return buildAnnotatedString {

        val lines = text.split("\n")

        lines.forEachIndexed { index, rawLine ->

            val trimmedStart = rawLine.trimStart()

            val isBullet = trimmedStart.startsWith("- ") || trimmedStart.startsWith("* ")

            val line = if (isBullet) {
                "• " + trimmedStart.removePrefix("- ").removePrefix("* ")
            } else {
                rawLine
            }

            var lastIndex = 0

            for (match in BOLD_REGEX.findAll(line)) {

                append(line.substring(lastIndex, match.range.first))

                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(match.groupValues[1])
                }

                lastIndex = match.range.last + 1

            }

            append(line.substring(lastIndex))

            if (index != lines.lastIndex) append("\n")

        }

    }

}

@Composable
private fun MessageBubble(message: ChatTurn) {

    val isUser = message.role == ChatRole.USER

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {

        Surface(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = Dimens.Radius16,
                topEnd = Dimens.Radius16,
                bottomStart = if (isUser) Dimens.Radius16 else Dimens.Space4,
                bottomEnd = if (isUser) Dimens.Space4 else Dimens.Radius16
            ),
            color = if (isUser)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant
        ) {

            Text(
                text = if (isUser) AnnotatedString(message.content) else formatMarkdownLite(message.content),
                modifier = Modifier.padding(Dimens.Space12),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isUser)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

    }


}

@Composable
private fun TypingBubble() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {

        Surface(
            shape = RoundedCornerShape(
                topStart = Dimens.Radius16,
                topEnd = Dimens.Radius16,
                bottomStart = Dimens.Space4,
                bottomEnd = Dimens.Radius16
            ),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {

            Box(
                modifier = Modifier.padding(Dimens.Space16),
                contentAlignment = Alignment.Center
            ) {

                MohaliqLoadingIndicator(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onPrimary
                )

            }

        }

    }

}

@Composable
private fun InputBar(
    value: String,
    canSend: Boolean,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Space12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Ask about hotels, restaurants...") },
            shape = RoundedCornerShape(Dimens.Radius24)
        )

        IconButton(
            onClick = onSendClick,
            enabled = canSend,
            modifier = Modifier
                .background(
                    color = if (canSend)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(50)
                )
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "Send",
                tint = if (canSend)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

    }

}
