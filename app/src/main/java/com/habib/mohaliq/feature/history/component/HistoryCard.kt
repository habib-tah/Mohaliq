package com.habib.mohaliq.feature.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.history.model.HistoryItem
import com.habib.mohaliq.feature.history.model.HistoryStatus

@Composable
fun HistoryCard(
    entry: HistoryItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    MohaliqCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.titleSmall
                )

                val statusColor =
                    if (entry.status == HistoryStatus.UPCOMING)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant

                Text(
                    text = stringResource(entry.status.labelRes),
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor,
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = .12f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = Dimens.Space8, vertical = Dimens.Space4)
                )

            }

            Text(
                text = entry.location,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = entry.dateRange,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "${entry.guestCount} guests",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "$${entry.price}",
                    style = MaterialTheme.typography.titleMedium
                )

            }

        }

    }

}
