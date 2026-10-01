package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.core.designsystem.theme.Dimens

/**
 * Labeled +/- stepper. Used for guest counts (Booking) and party size
 * (Restaurant Reservation) — anywhere a small bounded integer is picked.
 */
@Composable
fun MohaliqStepper(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    minValue: Int = 1,
    maxValue: Int = 10
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(Dimens.Radius24)
            )
            .padding(horizontal = Dimens.Space16, vertical = Dimens.Space12),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {

            IconButton(
                onClick = onDecrement,
                enabled = value > minValue
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Decrease $label"
                )
            }

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = onIncrement,
                enabled = value < maxValue
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increase $label"
                )
            }

        }

    }

}
