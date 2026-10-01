package com.habib.mohaliq.feature.restaurant.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.MenuItem

@Composable
fun MenuPreviewSection(
    items: List<MenuItem>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
    ) {

        items.forEachIndexed { index, item ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleMedium
                )

            }

            if (index != items.lastIndex) {
                HorizontalDivider()
            }

        }

    }

}
