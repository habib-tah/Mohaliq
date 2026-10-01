package com.habib.mohaliq.feature.hotel.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqInputField
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqStepper
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.hotel.booking.component.BookingRoomCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    uiState: BookingUiState,
    onAction: (BookingAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    MohaliqScaffold(

        snackbarHostState = snackbarHostState,

        topBar = {

            MohaliqTopBar(
                title = stringResource(R.string.booking_title),
                navigationIcon = R.drawable.ic_arrow_left_outline,
                onNavigationClick = {
                    onAction(BookingAction.BackClicked)
                }
            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = Dimens.Space20),

            verticalArrangement = Arrangement.spacedBy(Dimens.Space20)

        ) {

            Spacer(modifier = Modifier.height(Dimens.Space8))

            Text(
                text = uiState.hotelName,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = uiState.location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            MohaliqInputField(

                value = uiState.checkIn.ifBlank { stringResource(R.string.booking_select_date) },

                label = stringResource(R.string.booking_check_in),

                icon = R.drawable.ic_calendar_outline,

                onClick = {

                    onAction(BookingAction.CheckInClicked)

                }

            )

            MohaliqInputField(

                value = uiState.checkOut.ifBlank { stringResource(R.string.booking_select_date) },

                label = stringResource(R.string.booking_check_out),

                icon = R.drawable.ic_calendar_outline,

                onClick = {

                    onAction(BookingAction.CheckOutClicked)

                }

            )

            MohaliqStepper(
                label = stringResource(R.string.booking_guests),
                value = uiState.guestCount,
                onIncrement = { onAction(BookingAction.GuestIncremented) },
                onDecrement = { onAction(BookingAction.GuestDecremented) }
            )

            Text(
                text = stringResource(R.string.booking_available_rooms),
                style = MaterialTheme.typography.titleMedium
            )

            uiState.rooms.forEach { room ->

                BookingRoomCard(

                    room = room,

                    expanded = uiState.expandedRoomId == room.id,

                    selected = uiState.selectedRoomId == room.id,

                    onExpand = {

                        onAction(BookingAction.RoomExpandToggled(room.id))

                    },

                    onBook = {

                        onAction(BookingAction.BookRoom(room.id))

                    }

                )

            }

            Spacer(modifier = Modifier.height(Dimens.Space12))

            Button(

                modifier = Modifier.fillMaxWidth(),

                enabled = uiState.selectedRoomId != null,

                onClick = {

                    onAction(BookingAction.ContinueClicked)

                }

            ) {

                Text(stringResource(R.string.continue_booking))

            }

            Spacer(modifier = Modifier.height(Dimens.Space24))

        }

    }

    if (uiState.showCheckInPicker) {

        val state = rememberDatePickerState(
            initialSelectedDateMillis = uiState.checkInMillis
        )

        DatePickerDialog(
            onDismissRequest = { onAction(BookingAction.DatePickerDismissed) },
            confirmButton = {
                Button(
                    onClick = {
                        val millis = state.selectedDateMillis
                        if (millis != null) {
                            onAction(
                                BookingAction.CheckInSelected(
                                    date = dateFormatter.format(Date(millis)),
                                    millis = millis
                                )
                            )
                        } else {
                            onAction(BookingAction.DatePickerDismissed)
                        }
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        ) {
            DatePicker(state = state)
        }

    }

    if (uiState.showCheckOutPicker) {

        val state = rememberDatePickerState(
            initialSelectedDateMillis = uiState.checkOutMillis,
            selectableDates = object : androidx.compose.material3.SelectableDates {

                override fun isSelectableDate(
                    utcTimeMillis: Long
                ): Boolean {

                    val checkInMillis = uiState.checkInMillis

                    return checkInMillis == null ||
                            utcTimeMillis > checkInMillis
                }

            }
        )

        DatePickerDialog(
            onDismissRequest = { onAction(BookingAction.DatePickerDismissed) },
            confirmButton = {
                Button(
                    onClick = {
                        val millis = state.selectedDateMillis
                        if (millis != null) {
                            onAction(
                                BookingAction.CheckOutSelected(
                                    date = dateFormatter.format(Date(millis)),
                                    millis = millis
                                )
                            )
                        } else {
                            onAction(BookingAction.DatePickerDismissed)
                        }
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        ) {
            DatePicker(state = state)
        }

    }

}
