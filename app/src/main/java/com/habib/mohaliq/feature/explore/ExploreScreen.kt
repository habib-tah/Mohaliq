package com.habib.mohaliq.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.navigation.MohaliqBottomBar
import com.habib.mohaliq.core.designsystem.component.travel.TravelSection
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.explore.component.ExploreBanner
import com.habib.mohaliq.feature.explore.component.ExploreHeader

@Composable
fun ExploreScreen(
    uiState: ExploreUiState,
    onAction: (ExploreAction) -> Unit,
    onBottomNavigation: (Destinations) -> Unit
) {

    MohaliqScaffold(

        bottomBar = {

            MohaliqBottomBar(

                selectedIndex = uiState.selectedBottomBar,

                onItemSelected = { index ->

                    onAction(
                        ExploreAction.BottomBarSelected(index)
                    )

                    when (index) {

                        0 -> onBottomNavigation(Destinations.Home)

                        1 -> onBottomNavigation(Destinations.History)

                        2 -> onBottomNavigation(Destinations.Explore)

                        3 -> onBottomNavigation(Destinations.Favorites)

                        4 -> onBottomNavigation(Destinations.Profile)

                    }

                }

            )

        }

    ) { padding ->

        ExploreContent(

            padding = padding,

            uiState = uiState,

            onAction = onAction

        )

    }

}

@Composable
private fun ExploreContent(

    padding: PaddingValues,

    uiState: ExploreUiState,

    onAction: (ExploreAction) -> Unit

) {

    LazyColumn(

        modifier = Modifier
            .fillMaxSize(),

        contentPadding = PaddingValues(
            start = Dimens.Space20,
            end = Dimens.Space20,
            top = padding.calculateTopPadding(),
            bottom = padding.calculateBottomPadding() + Dimens.Space20
        ),

        verticalArrangement = Arrangement.spacedBy(Dimens.Space24)

    ) {

        item {

            ExploreHeader(

                location = uiState.location,

                onChangeLocation = {

                    onAction(
                        ExploreAction.ChangeLocationClicked
                    )

                }

            )

        }

        uiState.banner?.let { banner ->

            item {

                ExploreBanner(
                    banner = banner
                )

            }

        }

        item {

            TravelSection(

                title = stringResource(R.string.section_popular),

                items = uiState.popular,

                largeCards = false,

                onItemClick = {

                    onAction(
                        ExploreAction.PopularClicked(it.id)
                    )

                },

                onFavoriteClick = {

                    onAction(
                        ExploreAction.FavoriteClicked(it.id)
                    )

                },

                onSeeMoreClick = {

                    onAction(
                        ExploreAction.PopularSeeMoreClicked
                    )

                }

            )

        }

        item {

            TravelSection(

                title = stringResource(R.string.section_frequently_visited),

                items = uiState.frequentlyVisited,

                largeCards = true,

                onItemClick = {

                    onAction(
                        ExploreAction.FrequentlyVisitedClicked(it.id)
                    )

                },

                onFavoriteClick = {

                    onAction(
                        ExploreAction.FavoriteClicked(it.id)
                    )

                },

                onSeeMoreClick = {

                    onAction(
                        ExploreAction.FrequentlyVisitedSeeMoreClicked
                    )

                }

            )

        }

        item {

            TravelSection(

                title = stringResource(R.string.section_nearest_hotel),

                items = uiState.nearestHotels,

                largeCards = true,

                onItemClick = {

                    onAction(
                        ExploreAction.NearestHotelClicked(it.id)
                    )

                },

                onFavoriteClick = {

                    onAction(
                        ExploreAction.FavoriteClicked(it.id)
                    )

                },

                onSeeMoreClick = {

                    onAction(
                        ExploreAction.NearestHotelSeeMoreClicked
                    )

                }

            )

        }

    }

}