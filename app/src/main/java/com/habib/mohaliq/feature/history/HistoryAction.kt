package com.habib.mohaliq.feature.history

import com.habib.mohaliq.feature.history.model.HistoryStatus

sealed interface HistoryAction {

    data class FilterSelected(val status: HistoryStatus?) : HistoryAction

    data class EntryClicked(val entryId: String) : HistoryAction

    data class BottomBarItemSelected(val index: Int) : HistoryAction

}
