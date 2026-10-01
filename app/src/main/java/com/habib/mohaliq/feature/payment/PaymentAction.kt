package com.habib.mohaliq.feature.payment

sealed interface PaymentAction {

    data object BackClicked : PaymentAction

    data class MethodSelected(val methodId: String) : PaymentAction

    data object ConfirmClicked : PaymentAction

    data object DoneClicked : PaymentAction

}
