package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.habib.mohaliq.core.designsystem.theme.Dimens

@Composable
fun MohaliqChip(
    text: String,
    modifier: Modifier = Modifier,
    iconRes: Int? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    selectedContainerColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContainerColor: Color = MaterialTheme.colorScheme.surface,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {

    val containerColor =
        if (selected) selectedContainerColor
        else unselectedContainerColor

    val contentColor =
        if (selected) selectedContentColor
        else unselectedContentColor

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = Dimens.Space32)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(Dimens.Radius16)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(
                horizontal = Dimens.Space16,
                vertical = Dimens.Space8
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space8),
        verticalAlignment = Alignment.CenterVertically
    ) {

        iconRes?.let {

            Icon(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(Dimens.Space20),
                tint = contentColor
            )

        }

        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}