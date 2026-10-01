package com.habib.mohaliq.core.designsystem.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.TravelCardItem

/**
 * Full-width horizontal row: thumbnail + title/location/rating + price.
 * For vertical lists (Favorites, search results) — the carousel-style
 * TravelCompactCard/TravelLargeCard are for horizontal TravelSection rows.
 */
@Composable
fun TravelListItem(
    item: TravelCardItem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    MohaliqCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {

            Box(
                modifier = Modifier
                    .size(width = 96.dp, height = 96.dp)
                    .clip(RoundedCornerShape(Dimens.Radius12))
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {

                if (item.imageUrl != null) {

                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                item.badge?.let {

                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                }

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                RatingRow(
                    rating = item.rating,
                    reviewCount = item.reviewCount,
                    modifier = Modifier.padding(top = Dimens.Space4)
                )

                if (item.currentPrice > 0) {

                    Text(
                        text = "$${item.currentPrice}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = Dimens.Space4)
                    )

                }

            }

            IconButton(onClick = onFavoriteClick) {

                Icon(
                    painter = painterResource(
                        if (item.isFavorite) R.drawable.ic_heart_tick else R.drawable.ic_love_outline
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

            }

        }

    }

}
