package com.habib.mohaliq.feature.hotel.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqOutlinedActionButton
import com.habib.mohaliq.core.designsystem.component.RatingRow
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.hotel.HotelUiState

@Composable
fun HotelInfoSection(
    uiState: HotelUiState,
    onShowMap: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Space12),
        modifier = Modifier.padding(horizontal = Dimens.Space20)

    ) {

        Text(
            text = uiState.starLabel
        )

        Text(
            text = uiState.hotelName,
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_pin),
                contentDescription = null,
                modifier = Modifier.size(Dimens.Space16)
            )

            Text(
                text = uiState.location
            )

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
                onClick = onShowMap
            )

        }

    }

}
