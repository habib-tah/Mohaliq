package com.habib.mohaliq.feature.payment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.payment.model.PaymentMethod

@Composable
fun PaymentMethodRow(
    method: PaymentMethod,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {

    MohaliqCard(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelected
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {

            // Brand logos are real multi-color vectors — Image (not Icon) so
            // they render with their own colors instead of being tinted.
            Image(
                painter = painterResource(method.icon),
                contentDescription = null,
                modifier = Modifier
                    .width(48.dp)
                    .height(40.dp)
            )

            Text(
                text = method.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            RadioButton(
                selected = selected,
                onClick = onSelected
            )

        }

    }

}
