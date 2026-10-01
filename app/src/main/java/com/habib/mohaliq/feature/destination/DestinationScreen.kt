package com.habib.mohaliq.feature.destination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqImageSlider
import com.habib.mohaliq.core.designsystem.component.MohaliqOutlinedActionButton
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.component.travel.TravelSection
import com.habib.mohaliq.core.designsystem.theme.Dimens

@Composable
fun DestinationScreen(
    uiState: DestinationUiState,
    onAction: (DestinationAction) -> Unit
) {

    MohaliqScaffold { padding ->

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                bottom = padding.calculateBottomPadding() + Dimens.Space24
            ),

            verticalArrangement = Arrangement.spacedBy(Dimens.Space20)

        ) {

            item {

                Box {

                    MohaliqImageSlider(imageUrls = listOf(uiState.imageUrl))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.Space16),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(onClick = { onAction(DestinationAction.BackClicked) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_left_outline),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { onAction(DestinationAction.FavoriteClicked) }) {
                            Icon(
                                painter = painterResource(
                                    if (uiState.isFavorite) R.drawable.ic_heart_tick else R.drawable.ic_love_outline
                                ),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }

                }

            }

            item {

                Column(
                    modifier = Modifier.padding(horizontal = Dimens.Space20),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
                ) {

                    Text(
                        text = uiState.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            painter = painterResource(R.drawable.ic_pin),
                            contentDescription = null,
                            modifier = Modifier.padding(end = Dimens.Space4)
                        )

                        Text(text = uiState.location)

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RatingRow(
                            rating = uiState.rating,
                            reviewCount = uiState.reviews
                        )

                        MohaliqOutlinedActionButton(
                            text = stringResource(R.string.show_map),
                            icon = R.drawable.ic_gps_outline,
                            onClick = { onAction(DestinationAction.ShowMapClicked) }
                        )

                    }

                }

            }

            item { HorizontalDivider() }

            item {

                Text(
                    text = stringResource(R.string.overview),
                    modifier = Modifier.padding(horizontal = Dimens.Space20),
                    style = MaterialTheme.typography.titleMedium
                )

            }

            item {

                Text(
                    text = uiState.overview,
                    modifier = Modifier.padding(horizontal = Dimens.Space20),
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            item { HorizontalDivider() }

            item {

                TravelSection(
                    title = "Nearby",
                    items = uiState.nearbyPlaces,
                    largeCards = false,
                    onItemClick = { onAction(DestinationAction.NearbyItemClicked(it.id)) },
                    onFavoriteClick = { onAction(DestinationAction.NearbyFavoriteClicked(it.id)) },
                    onSeeMoreClick = { onAction(DestinationAction.NearbySeeMoreClicked) },
                    modifier = Modifier.padding(horizontal = Dimens.Space20)
                )

            }

        }

    }

}
