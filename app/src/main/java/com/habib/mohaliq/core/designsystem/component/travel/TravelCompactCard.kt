package com.habib.mohaliq.core.designsystem.component.travel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.TravelCardItem

@Composable
fun TravelCompactCard(
    item: TravelCardItem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    MohaliqCard(
        modifier = modifier
            .clickable(onClick = onClick),
        contentPadding = PaddingValues(0.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(Dimens.Radius24))
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

            // Dark gradient at the bottom for readable text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // Badge
            item.badge?.let {

                Surface(
                    modifier = Modifier
                        .padding(Dimens.Space12)
                        .align(Alignment.TopStart),
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surface
                ) {

                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            horizontal = Dimens.Space12,
                            vertical = Dimens.Space4
                        )
                    )
                }
            }

            // Favorite
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onFavoriteClick
            ) {

                Icon(
                    painter = painterResource(
                        if (item.isFavorite) {
                            R.drawable.ic_heart_tick
                        } else {
                            R.drawable.ic_love_outline
                        }
                    ),
                    contentDescription = null
                )
            }

            // Content over image
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(Dimens.Space12)
            ) {

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = Dimens.Space4)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.Space8),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RatingRow(
                        rating = item.rating,
                        reviewCount = item.reviewCount,
                        showReviewCount = false,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Text(
                        text = "$${item.currentPrice}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = Dimens.Space8)
                    )
                }
            }
        }
    }
}