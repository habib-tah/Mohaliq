package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.habib.mohaliq.core.designsystem.theme.Dimens

/**
 * Small pill-shaped outlined button with a label + trailing icon.
 * Used for "Show map" (Hotel/Restaurant detail) and similar secondary actions.
 */
@Composable
fun MohaliqOutlinedActionButton(
    text: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            Dimens.Space4 / 4,
            MaterialTheme.colorScheme.primary
        )
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = text)

            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )

        }

    }

}
