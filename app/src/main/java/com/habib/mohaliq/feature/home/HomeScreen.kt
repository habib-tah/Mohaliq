package com.habib.mohaliq.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.navigation.MohaliqBottomBar
import com.habib.mohaliq.core.designsystem.component.travel.TravelSection
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.home.component.CategoryRow
import com.habib.mohaliq.feature.home.component.HomeHeader

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    onBottomNavigation: (Destinations) -> Unit
) {

    MohaliqScaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = { onAction(HomeAction.AiAssistantClicked) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_magicpen),
                    contentDescription = "AI Assistant"
                )

            }

        },

        bottomBar = {

            MohaliqBottomBar(

                selectedIndex = uiState.selectedBottomBarIndex,

                onItemSelected = { index ->

                    onAction(HomeAction.BottomBarItemSelected(index))

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

    ) { innerPadding ->

        HomeContent(

            innerPadding = innerPadding,

            uiState = uiState,

            onAction = onAction

        )

    }

}

@Composable
private fun HomeContent(
    innerPadding: PaddingValues,
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) {

    LazyColumn(

        modifier = Modifier.fillMaxSize(),

        contentPadding = innerPadding

    ) {

        item {

            HomeHeader(

                searchQuery = uiState.searchQuery,

                onSearchQueryChange = {

                    onAction(HomeAction.SearchQueryChanged(it))

                },

                onSearchBarClick = {

                    onAction(HomeAction.SearchBarClicked)

                }

            )

        }


        item {

            Box(
                modifier = Modifier.padding(vertical = Dimens.Space16)
            ) {

                CategoryRow(

                    categories = uiState.categories,

                    onCategoryClick = {

                        onAction(HomeAction.CategoryClicked(it))

                    }

                )

            }

        }

        item {

            TravelSection(
                title = stringResource(R.string.section_popular),
                items = uiState.popularItems,
                largeCards = true,
                modifier = Modifier.padding(horizontal = Dimens.Space20),

                onItemClick = {

                    onAction(HomeAction.PopularClicked(it))

                },

                onFavoriteClick = {

                    onAction(HomeAction.FavoriteClicked(it.id))

                },

                onSeeMoreClick = {

                    onAction(HomeAction.PopularSeeMoreClicked)

                }

            )

            Spacer(modifier = Modifier.padding(vertical = Dimens.Space16))

        }

        item {

            TravelSection(
                title = stringResource(R.string.section_recommendation),
                items = uiState.recommendationItems,
                largeCards = false,
                modifier = Modifier.padding(horizontal = Dimens.Space20),

                onItemClick = {

                    onAction(HomeAction.RecommendationClicked(it))

                },

                onFavoriteClick = {

                    onAction(HomeAction.FavoriteClicked(it.id))

                },

                onSeeMoreClick = {

                    onAction(HomeAction.RecommendationSeeMoreClicked)

                }

            )

        }

        item {

            Spacer(
                modifier = Modifier.height(Dimens.Space20)
            )

        }

    }

}