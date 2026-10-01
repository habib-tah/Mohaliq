package com.habib.mohaliq.feature.restaurant.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqInputField
import com.habib.mohaliq.core.designsystem.component.MohaliqStepper
import com.habib.mohaliq.core.designsystem.theme.Dimens

@Composable
fun ReservationSection(

    reservationDate: String,

    partySize: Int,

    onDateClick: () -> Unit,

    onIncrementPartySize: () -> Unit,

    onDecrementPartySize: () -> Unit,

    onReserveClick: () -> Unit,

    modifier: Modifier = Modifier

) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {

        MohaliqInputField(
            value = reservationDate.ifBlank { "Select date" },
            label = stringResource(R.string.reservation_date),
            icon = R.drawable.ic_calendar_outline,
            onClick = onDateClick
        )

        MohaliqStepper(
            label = stringResource(R.string.party_size),
            value = partySize,
            onIncrement = onIncrementPartySize,
            onDecrement = onDecrementPartySize,
            maxValue = 12
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onReserveClick
        ) {

            Text(stringResource(R.string.reserve_now))

        }

    }

}
