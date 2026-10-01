package com.habib.mohaliq.feature.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqLoadingIndicator
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.payment.component.PaymentMethodRow

@Composable
fun PaymentScreen(
    uiState: PaymentUiState,
    onAction: (PaymentAction) -> Unit
) {

    if (uiState.isSuccess) {

        PaymentSuccessContent(
            title = uiState.successTitle,
            message = uiState.successMessage,
            onDoneClick = {
                onAction(PaymentAction.DoneClicked)
            }
        )

        return
    }

    MohaliqScaffold(
        topBar = {
            MohaliqTopBar(
                title = "Payment",
                navigationIcon = R.drawable.ic_arrow_left_outline,
                onNavigationClick = {
                    onAction(PaymentAction.BackClicked)
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.Space20),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(Dimens.Space8)
                )
            }

            item {
                Text(
                    text = uiState.itemName,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                Text(
                    text = uiState.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space4)
                ) {
                    PaymentSummaryRow(
                        label = "Dates",
                        value = uiState.dateRange
                    )

                    PaymentSummaryRow(
                        label = "Guests",
                        value = "${uiState.guestCount}"
                    )

                    PaymentSummaryRow(
                        label = "Total Price",
                        value = "$${uiState.totalPrice}",
                        emphasize = true
                    )
                }
            }

            item {
                Text(
                    text = "Select Payment Method",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        top = Dimens.Space8
                    )
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
                ) {
                    uiState.paymentMethods.forEach { method ->

                        PaymentMethodRow(
                            method = method,
                            selected = uiState.selectedMethodId == method.id,
                            onSelected = {
                                onAction(
                                    PaymentAction.MethodSelected(method.id)
                                )
                            }
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(Dimens.Space12)
                )
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.canConfirm,
                    onClick = {
                        onAction(PaymentAction.ConfirmClicked)
                    }
                ) {
                    if (uiState.isProcessing) {

                        MohaliqLoadingIndicator(
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    } else {

                        Text("Confirm & Pay")
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(Dimens.Space24)
                )
            }
        }
    }
}

@Composable
private fun PaymentSummaryRow(
    label: String,
    value: String,
    emphasize: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = if (emphasize) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            }
        )
    }
}

@Composable
private fun PaymentSuccessContent(
    title: String,
    message: String,
    onDoneClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Icon(
                painter = painterResource(
                    R.drawable.ic_tick_circle_two_tone
                ),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(96.dp)
            )

            Spacer(
                modifier = Modifier.height(Dimens.Space24)
            )
        }
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(Dimens.Space8)
            )
        }
        item {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(Dimens.Space32)
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDoneClick
            ) {
                Text("Continue")
            }
        }
    }
}