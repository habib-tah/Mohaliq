package com.habib.mohaliq.core.designsystem.component.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.habib.mohaliq.core.designsystem.component.SectionHeader
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.TravelCardItem

@Composable
fun TravelSection(
    title: String,
    items: List<TravelCardItem>,
    largeCards: Boolean,
    onItemClick: (TravelCardItem) -> Unit,
    onFavoriteClick: (TravelCardItem) -> Unit,
    onSeeMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {

        SectionHeader(
            title = title,
            onSeeMoreClick = onSeeMoreClick
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {

            items(items) { item ->

                if (largeCards) {

                    TravelLargeCard(
                        item = item,
                        onClick = {
                            onItemClick(item)
                        },
                        onFavoriteClick = {
                            onFavoriteClick(item)
                        },
                        modifier = Modifier.fillParentMaxWidth(0.82f)
                    )

                } else {

                    TravelCompactCard(
                        item = item,
                        onClick = {
                            onItemClick(item)
                        },
                        onFavoriteClick = {
                            onFavoriteClick(item)
                        },
                        modifier = Modifier.fillParentMaxWidth(0.48f)
                    )

                }

            }

        }

    }

}