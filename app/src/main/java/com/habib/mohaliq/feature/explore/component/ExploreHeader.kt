package com.habib.mohaliq.feature.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.designsystem.theme.Primary

@Composable
fun ExploreHeader(

    location: String,

    onChangeLocation: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Space20),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(

                painter = painterResource(R.drawable.ic_pin),

                contentDescription = null,

                tint = Primary

            )

            Text(

                text = location,

                modifier = Modifier.padding(start = Dimens.Space8),

                style = MaterialTheme.typography.bodyLarge

            )

        }

        Button(

            onClick = onChangeLocation,

            shape = RoundedCornerShape(50),

            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )

        ) {

            Text(stringResource(R.string.change_location))

        }

    }

}