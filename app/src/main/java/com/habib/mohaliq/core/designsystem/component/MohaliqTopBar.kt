package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.habib.mohaliq.core.designsystem.theme.Dimens

@Composable
fun MohaliqTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: Int? = null,
    onNavigationClick: (() -> Unit)? = null,
    actionIcon: Int? = null,
    onActionClick: (() -> Unit)? = null,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.Space20,
                vertical = Dimens.Space16
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (navigationIcon != null) {

            IconButton(
                onClick = { onNavigationClick?.invoke() }
            ) {
                Icon(
                    painter = painterResource(navigationIcon),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.Space24),
                    tint = contentColor
                )
            }

        } else {
            Spacer24()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = contentColor
        )

        if (actionIcon != null) {

            IconButton(
                onClick = { onActionClick?.invoke() }
            ) {
                Icon(
                    painter = painterResource(actionIcon),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.Space24),
                    tint = contentColor
                )
            }

        } else {
            Spacer24()
        }
    }
}

@Composable
private fun Spacer24() {
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier.size(Dimens.Space24)
    )
}