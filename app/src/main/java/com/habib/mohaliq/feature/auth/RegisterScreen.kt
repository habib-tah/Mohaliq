package com.habib.mohaliq.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqLoadingIndicator
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTextField
import com.habib.mohaliq.core.designsystem.theme.Dimens

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onAction: (RegisterAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    MohaliqScaffold(
        snackbarHostState = snackbarHostState
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(Dimens.Space20),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Create account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Sign up to start planning your next trip",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = Dimens.Space8, bottom = Dimens.Space24)
            )

            MohaliqTextField(
                value = uiState.name,
                onValueChange = { onAction(RegisterAction.NameChanged(it)) },
                label = "Full name",
                leadingIcon = R.drawable.ic_user_outline,
                isError = uiState.nameError != null,
                supportingText = uiState.nameError
            )

            Spacer(modifier = Modifier.height(Dimens.Space16))

            MohaliqTextField(
                value = uiState.email,
                onValueChange = { onAction(RegisterAction.EmailChanged(it)) },
                label = "Email",
                leadingIcon = R.drawable.ic_sms_outline,
                keyboardType = KeyboardType.Email,
                isError = uiState.emailError != null,
                supportingText = uiState.emailError
            )

            Spacer(modifier = Modifier.height(Dimens.Space16))

            MohaliqTextField(
                value = uiState.password,
                onValueChange = { onAction(RegisterAction.PasswordChanged(it)) },
                label = "Password",
                leadingIcon = R.drawable.ic_lock_outline,
                isPassword = true,
                isError = uiState.passwordError != null,
                supportingText = uiState.passwordError
            )

            Spacer(modifier = Modifier.height(Dimens.Space16))

            MohaliqTextField(
                value = uiState.confirmPassword,
                onValueChange = { onAction(RegisterAction.ConfirmPasswordChanged(it)) },
                label = "Confirm password",
                leadingIcon = R.drawable.ic_lock_outline,
                isPassword = true,
                isError = uiState.confirmPasswordError != null,
                supportingText = uiState.confirmPasswordError
            )

            Spacer(modifier = Modifier.height(Dimens.Space24))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.canSubmit,
                onClick = { onAction(RegisterAction.RegisterClicked) }
            ) {

                if (uiState.isLoading) {
                    MohaliqLoadingIndicator(
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Register")
                }

            }

            Spacer(modifier = Modifier.height(Dimens.Space12))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(RegisterAction.GoogleSignUpClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.social_google_default),
                    contentDescription = "Google",
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(Dimens.Space8))

                Text("Sign up with Google")
            }

            Spacer(modifier = Modifier.height(Dimens.Space24))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Already have an account? ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Sign in",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onAction(RegisterAction.LoginLinkClicked) }
                )

            }

        }

    }

}
