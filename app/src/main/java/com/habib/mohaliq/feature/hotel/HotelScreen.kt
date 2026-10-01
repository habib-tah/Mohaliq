package com.habib.mohaliq.feature.hotel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqImageSlider
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.hotel.component.FacilityGrid
import com.habib.mohaliq.feature.hotel.component.HotelInfoSection
import com.habib.mohaliq.feature.hotel.component.RoomChipGroup

@Composable
fun HotelScreen(
    uiState: HotelUiState,
    onAction: (HotelAction) -> Unit
) {

    MohaliqScaffold(

        bottomBar = {

            Button(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.Space20,
                        vertical = Dimens.Space16
                    )
                    .fillMaxWidth(),
                onClick = {

                    onAction(
                        HotelAction.BookingClicked
                    )

                }

            ) {

                Text(
                    stringResource(R.string.booking)
                )

            }

        }

    ) { padding ->

        HotelContent(

            padding = padding,

            uiState = uiState,

            onAction = onAction

        )

    }

}

@Composable
private fun HotelContent(

    padding: PaddingValues,

    uiState: HotelUiState,

    onAction: (HotelAction) -> Unit

) {

    LazyColumn(

        modifier = Modifier.fillMaxSize(),

        contentPadding = PaddingValues(
            bottom = padding.calculateBottomPadding() + 24.dp
        ),

        verticalArrangement = Arrangement.spacedBy(Dimens.Space20)

    ) {

        item {

            Box {

                MohaliqImageSlider(
                    imageUrls = listOf(uiState.imageUrl)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.Space16),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        onClick = {
                            onAction(HotelAction.BackClicked)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_outline),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { onAction(HotelAction.FavoriteClicked) }) {
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

            HotelInfoSection(

                uiState = uiState,

                onShowMap = {

                    onAction(
                        HotelAction.ShowMapClicked
                    )

                }

            )

        }

        item {

            HorizontalDivider()

        }

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

        item {

            Text(

                text = stringResource(R.string.facilities),

                modifier = Modifier.padding(horizontal = Dimens.Space20),

                style = MaterialTheme.typography.titleMedium

            )

        }

        item {

            Box(
                modifier = Modifier.padding(horizontal = Dimens.Space20)
            ) {

                FacilityGrid(
                    facilities = uiState.facilities
                )

            }

        }

        if (uiState.rooms.isNotEmpty()) {

            item {

                Text(

                    text = stringResource(R.string.room),

                    modifier = Modifier.padding(horizontal = Dimens.Space20),

                    style = MaterialTheme.typography.titleMedium

                )

            }

            item {

                Box(
                    modifier = Modifier.padding(horizontal = Dimens.Space20)
                ) {

                    RoomChipGroup(

                        rooms = uiState.rooms,

                        onRoomSelected = {

                            onAction(
                                HotelAction.RoomSelected(it.id)
                            )

                        }

                    )

                }

            }

        }

    }

}