package com.habib.mohaliq.feature.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqImageSlider
import com.habib.mohaliq.core.designsystem.component.MohaliqOutlinedActionButton
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.restaurant.component.MenuPreviewSection
import com.habib.mohaliq.feature.restaurant.component.ReservationSection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    uiState: RestaurantUiState,
    onAction: (RestaurantAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    MohaliqScaffold (
        snackbarHostState = snackbarHostState
    ){ padding ->

        RestaurantContent(
            padding = padding,
            uiState = uiState,
            onAction = onAction
        )

    }

    if (uiState.showDatePicker) {

        val state = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { onAction(RestaurantAction.DatePickerDismissed) },
            confirmButton = {
                Button(
                    onClick = {
                        val millis = state.selectedDateMillis
                        if (millis != null) {
                            onAction(
                                RestaurantAction.DateSelected(
                                    dateFormatter.format(Date(millis))
                                )
                            )
                        } else {
                            onAction(RestaurantAction.DatePickerDismissed)
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = state)
        }

    }

}

@Composable
private fun RestaurantContent(
    padding: PaddingValues,
    uiState: RestaurantUiState,
    onAction: (RestaurantAction) -> Unit
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

                MohaliqImageSlider(imageUrls = listOf(uiState.imageUrl))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.Space16),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(onClick = { onAction(RestaurantAction.BackClicked) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_outline),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { onAction(RestaurantAction.FavoriteClicked) }) {
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
                    text = uiState.restaurantName,
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

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
                        onClick = { onAction(RestaurantAction.ShowMapClicked) }
                    )

                }

            }

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

        if(uiState.menuItems.isNotEmpty()) {

            item {
                HorizontalDivider()
            }

            item {

                Text(
                    text = stringResource(R.string.menu),
                    modifier = Modifier.padding(horizontal = Dimens.Space20),
                    style = MaterialTheme.typography.titleMedium
                )

            }

            item {

                MenuPreviewSection(
                    items = uiState.menuItems,
                    modifier = Modifier.padding(horizontal = Dimens.Space20)
                )

            }

        }

        item {
            HorizontalDivider()
        }

        item {

            Text(
                text = stringResource(R.string.reservation),
                modifier = Modifier.padding(horizontal = Dimens.Space20),
                style = MaterialTheme.typography.titleMedium
            )

        }

        item {

            ReservationSection(
                reservationDate = uiState.reservationDate,
                partySize = uiState.partySize,
                onDateClick = { onAction(RestaurantAction.DateFieldClicked) },
                onIncrementPartySize = { onAction(RestaurantAction.PartySizeIncremented) },
                onDecrementPartySize = { onAction(RestaurantAction.PartySizeDecremented) },
                onReserveClick = { onAction(RestaurantAction.ReserveClicked) },
                modifier = Modifier.padding(horizontal = Dimens.Space20)
            )

        }

    }

}
