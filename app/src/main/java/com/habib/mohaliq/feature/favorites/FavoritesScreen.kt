package com.habib.mohaliq.feature.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.designsystem.component.MohaliqChip
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.component.navigation.MohaliqBottomBar
import com.habib.mohaliq.core.designsystem.component.travel.TravelListItem
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.favorites.model.FavoriteCategory


@Composable
fun FavoritesScreen(
    uiState: FavoritesUiState,
    onAction: (FavoritesAction) -> Unit,
    onBottomNavigation: (Destinations) -> Unit
) {

    MohaliqScaffold(

        topBar = {
            MohaliqTopBar(title = stringResource(R.string.favorites_title))
        },

        bottomBar = {

            MohaliqBottomBar(

                selectedIndex = uiState.selectedBottomBarIndex,

                onItemSelected = { index ->

                    onAction(FavoritesAction.BottomBarItemSelected(index))

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                horizontal = Dimens.Space20,
                vertical = Dimens.Space16
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {

            item {

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
                ) {

                    items(FavoriteCategory.entries) { category ->

                        MohaliqChip(
                            text = stringResource(category.labelRes),
                            selected = uiState.selectedCategory == category,
                            onClick = {
                                onAction(FavoritesAction.CategorySelected(category))
                            }
                        )

                    }

                }

            }

            if (uiState.visibleItems.isEmpty()) {

                item {

                    Text(
                        text = stringResource(R.string.favorites_no_items),
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                }

            }

            items(
                items = uiState.visibleItems,
                key = { it.item.id }
            ) { favorite ->

                TravelListItem(
                    item = favorite.item,
                    onClick = { onAction(FavoritesAction.ItemClicked(favorite.item.id)) },
                    onFavoriteClick = { onAction(FavoritesAction.FavoriteRemoved(favorite.item.id)) }
                )

            }

        }

    }

}
