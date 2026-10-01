package com.habib.mohaliq.core.designsystem.component.travel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.TravelCardItem

@Composable
fun TravelLargeCard(
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

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(Dimens.Radius24))
            ) {

                if (item.imageUrl != null) {

                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                item.badge?.let {

                    Surface(
                        modifier = Modifier
                            .padding(Dimens.Space12)
                            .align(Alignment.BottomStart),
                        shape = RoundedCornerShape(50)
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

                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = onFavoriteClick
                ) {

                    Icon(
                        painter = painterResource(
                            if (item.isFavorite) R.drawable.ic_heart_tick else R.drawable.ic_love_outline
                        ),
                        contentDescription = null
                    )

                }

            }

            Column(
                modifier = Modifier.padding(Dimens.Space16)
            ) {

                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(Dimens.Space8))

                Text(
                    item.location,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(Dimens.Space12))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RatingRow(
                        rating = item.rating,
                        reviewCount = item.reviewCount,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Row(verticalAlignment = Alignment.Bottom) {

                        Text(
                            "$${item.currentPrice}",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        item.originalPrice?.let {

                            Spacer(
                                modifier = Modifier.width(Dimens.Space8)
                            )

                            Text(
                                "$$it",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textDecoration = TextDecoration.LineThrough,
                                maxLines = 1
                            )

                        }

                    }

                }

            }

        }

    }

}
