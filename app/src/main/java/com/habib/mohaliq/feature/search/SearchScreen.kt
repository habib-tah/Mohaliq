package com.habib.mohaliq.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqChip
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqSearchBar
import com.habib.mohaliq.core.designsystem.component.travel.TravelListItem
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.search.model.SearchCategory

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onAction: (SearchAction) -> Unit
) {

    MohaliqScaffold { padding ->

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { onAction(SearchAction.CloseClicked) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_outline),
                            contentDescription = null
                        )
                    }

                    Text(text = stringResource(R.string.search_title))

                }

            }

            item {

                MohaliqSearchBar(
                    value = uiState.query,
                    onValueChange = { onAction(SearchAction.QueryChanged(it)) },
                    placeholder = stringResource(R.string.search_placeholder)
                )

            }

            item {

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
                ) {

                    items(SearchCategory.entries) { category ->

                        MohaliqChip(
                            text = stringResource(category.labelRes),
                            selected = uiState.selectedCategory == category,
                            onClick = { onAction(SearchAction.CategorySelected(category)) }
                        )

                    }

                }

            }

            if (uiState.visibleResults.isEmpty()) {

                item {
                    Text(
                        text = stringResource(R.string.search_no_results),
                        modifier = Modifier
                            .padding(top = 32.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

            }

            items(
                items = uiState.visibleResults,
                key = { it.item.id }
            ) { result ->

                TravelListItem(
                    item = result.item,
                    onClick = { onAction(SearchAction.ResultClicked(result.item.id)) },
                    onFavoriteClick = { onAction(SearchAction.FavoriteClicked(result.item.id)) }
                )

            }

        }

    }

}
