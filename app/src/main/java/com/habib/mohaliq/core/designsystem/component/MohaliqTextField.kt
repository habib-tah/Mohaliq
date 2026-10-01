package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.theme.Dimens

/**
 * Real editable text input — for anything that needs the user to type
 * (auth forms, future search-in-place, etc.). MohaliqInputField is for
 * read-only fields that open a picker instead (dates, guest counts).
 */
@Composable
fun MohaliqTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    supportingText: String? = null
) {

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = modifier.fillMaxWidth(),

        label = { Text(label) },

        shape = RoundedCornerShape(Dimens.Radius24),

        singleLine = true,

        isError = isError,

        supportingText = supportingText?.let { { Text(it) } },

        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType
        ),

        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,

        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null
                )
            }
        },

        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.ic_eye_outline else R.drawable.ic_eye_slash_outline
                        ),
                        contentDescription = null
                    )
                }
            }
        } else null

    )

}
