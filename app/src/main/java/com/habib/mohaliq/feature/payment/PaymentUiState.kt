package com.habib.mohaliq.feature.payment

import com.habib.mohaliq.feature.payment.model.PaymentMethod

data class PaymentUiState(

    val itemName: String = "",

    val location: String = "",

    val dateRange: String = "",

    val guestCount: Int = 1,

    val totalPrice: Double = 0.0,

    val paymentMethods: List<PaymentMethod> = emptyList(),

    val selectedMethodId: String? = null,

    val isProcessing: Boolean = false,

    val isSuccess: Boolean = false,

    val successTitle: String = "",

    val successMessage: String = ""

) {

    val canConfirm: Boolean
        get() = selectedMethodId != null && !isProcessing
}