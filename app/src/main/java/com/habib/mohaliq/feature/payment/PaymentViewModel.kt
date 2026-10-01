package com.habib.mohaliq.feature.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.R
import com.habib.mohaliq.core.data.repository.BookingRepository
import com.habib.mohaliq.core.data.repository.PlaceRepository
import com.habib.mohaliq.core.model.BookingStatus
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.feature.payment.model.PaymentMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val bookingRepository: BookingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val placeType: PlaceType =
        PlaceType.valueOf(checkNotNull(savedStateHandle["placeType"]))

    private val placeId: String =
        checkNotNull(savedStateHandle["placeId"])

    private val secondaryId: String =
        checkNotNull(savedStateHandle["secondaryId"])

    private val dateRange: String =
        checkNotNull(savedStateHandle["dateRange"])

    private val guestCount: Int =
        checkNotNull(savedStateHandle["guestCount"])

    private val _uiState = MutableStateFlow(
        PaymentUiState(
            dateRange = dateRange,
            guestCount = guestCount,
            successTitle = when (placeType) {
                PlaceType.HOTEL -> "Booking Success!"
                PlaceType.RESTAURANT -> "Reservation Success!"
                else -> "Success!"
            },
            successMessage = when (placeType) {
                PlaceType.HOTEL ->
                    "Your payment has been confirmed. Enjoy your trip!"

                PlaceType.RESTAURANT ->
                    "Your restaurant reservation has been confirmed."

                else ->
                    "Your payment has been confirmed."
            },
            paymentMethods = listOf(
                PaymentMethod(
                    "mastercard",
                    "MasterCard",
                    R.drawable.payment_master_card
                ),
                PaymentMethod(
                    "paypal",
                    "PayPal",
                    R.drawable.payment_paypal
                ),
                PaymentMethod(
                    "unionpay",
                    "UnionPay",
                    R.drawable.payment_union_pay
                )
            )
        )
    )

    val uiState = _uiState.asStateFlow()

    private val _events = Channel<PaymentEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadSummary()
    }

    fun onAction(action: PaymentAction) {
        when (action) {

            PaymentAction.BackClicked -> {
                viewModelScope.launch {
                    _events.send(PaymentEvent.NavigateBack)
                }
            }

            is PaymentAction.MethodSelected -> {
                _uiState.update {
                    it.copy(selectedMethodId = action.methodId)
                }
            }

            PaymentAction.ConfirmClicked -> {
                confirmPayment()
            }

            PaymentAction.DoneClicked -> {
                viewModelScope.launch {
                    _events.send(PaymentEvent.NavigateHome)
                }
            }
        }
    }

    private fun loadSummary() {
        viewModelScope.launch {

            when (placeType) {

                PlaceType.HOTEL -> {
                    loadHotelSummary()
                }

                PlaceType.RESTAURANT -> {
                    loadRestaurantSummary()
                }

                else -> Unit
            }
        }
    }

    private suspend fun loadHotelSummary() {

        val hotel = placeRepository.observeDetail(placeId)
        val room = placeRepository.getRoomOption(secondaryId)

        hotel.collect { detail ->

            if (detail == null) return@collect

            _uiState.update {
                it.copy(
                    itemName = detail.title,
                    location = detail.location,
                    totalPrice = room?.price ?: detail.currentPrice
                )
            }
        }
    }

    private suspend fun loadRestaurantSummary() {

        val restaurant = placeRepository.observeDetail(placeId)

        restaurant.collect { detail ->

            if (detail == null) return@collect

            _uiState.update {
                it.copy(
                    itemName = detail.title,
                    location = detail.location,
                    totalPrice = 0.0
                )
            }
        }
    }

    private fun confirmPayment() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(isProcessing = true)
            }

            // Simulate payment processing for the local demo flow.
            delay(1200.milliseconds)

            val state = _uiState.value

            bookingRepository.createBooking(
                placeId = placeId,
                placeType = placeType,
                title = state.itemName,
                location = state.location,
                dateRange = state.dateRange,
                guestCount = state.guestCount,
                price = state.totalPrice,
                status = BookingStatus.UPCOMING
            )

            _uiState.update {
                it.copy(
                    isProcessing = false,
                    isSuccess = true
                )
            }
        }
    }
}