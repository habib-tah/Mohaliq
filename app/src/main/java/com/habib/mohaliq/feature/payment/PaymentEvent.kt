package com.habib.mohaliq.feature.payment

sealed interface PaymentEvent {

    data object NavigateBack : PaymentEvent

    // Fired when the user taps "Continue" on the success screen —
    // takes them back to Home, clearing the booking flow off the back stack.
    data object NavigateHome : PaymentEvent
}