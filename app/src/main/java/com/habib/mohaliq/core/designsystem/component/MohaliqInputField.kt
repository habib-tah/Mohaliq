package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.habib.mohaliq.core.designsystem.theme.Dimens

/**
 * Read-only, clickable field used for anything that opens a picker instead
 * of accepting free text — dates, guest counts, locations.
 */
@Composable
fun MohaliqInputField(
    value: String,
    label: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        OutlinedTextField(

            modifier = Modifier.fillMaxWidth(),

            value = value,

            onValueChange = {},

            enabled = false,

            readOnly = true,

            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                Dimens.Radius24
            ),

            label = {
                Text(label)
            },

            trailingIcon = {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null
                )
            },

            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor =
                    androidx.compose.material3.MaterialTheme.colorScheme.onSurface,

                disabledLabelColor =
                    androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,

                disabledTrailingIconColor =
                    androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,

                disabledBorderColor =
                    androidx.compose.material3.MaterialTheme.colorScheme.outline
            )

        )

    }

}