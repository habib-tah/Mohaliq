package com.habib.mohaliq.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.mohaliq.core.data.repository.BookingRepository
import com.habib.mohaliq.core.model.BookingStatus
import com.habib.mohaliq.feature.history.model.HistoryItem
import com.habib.mohaliq.feature.history.model.HistoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private fun BookingStatus.toHistoryStatus() = when (this) {
    BookingStatus.UPCOMING -> HistoryStatus.UPCOMING
    BookingStatus.COMPLETED -> HistoryStatus.COMPLETED
}

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HistoryEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeBookings()
    }

    fun onAction(action: HistoryAction) {
        when (action) {

            is HistoryAction.FilterSelected -> {
                _uiState.update { it.copy(selectedFilter = action.status) }
            }

            is HistoryAction.EntryClicked -> {

                val entry = _uiState.value.entries.find { it.id == action.entryId }

                if (entry != null) {
                    viewModelScope.launch {
                        _events.send(HistoryEvent.NavigateToDetail(entry.placeId, entry.placeType))
                    }
                }

            }

            is HistoryAction.BottomBarItemSelected -> {
                _uiState.update { it.copy(selectedBottomBarIndex = action.index) }
            }

        }
    }

    private fun observeBookings() {
        viewModelScope.launch {
            bookingRepository.observeBookings().collect { bookings ->
                _uiState.update {
                    it.copy(
                        entries = bookings.map { booking ->
                            HistoryItem(
                                id = booking.id.toString(),
                                placeId = booking.placeId,
                                placeType = booking.placeType,
                                title = booking.title,
                                location = booking.location,
                                dateRange = booking.dateRange,
                                guestCount = booking.guestCount,
                                price = booking.price,
                                status = booking.status.toHistoryStatus()
                            )
                        }
                    )
                }
            }
        }
    }

}
