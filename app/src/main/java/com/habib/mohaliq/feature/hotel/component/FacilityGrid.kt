package com.habib.mohaliq.feature.hotel.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.hotel.model.FacilityItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilityGrid(
    facilities: List<FacilityItem>
) {

    FlowRow(

        maxItemsInEachRow = 2,

        horizontalArrangement = Arrangement.spacedBy(Dimens.Space20),

        verticalArrangement = Arrangement.spacedBy(Dimens.Space12)

    ) {

        facilities.forEach {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(it.icon),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.Space16),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(Dimens.Space8))

                Text(
                    text = it.title
                )

            }

        }

    }

}