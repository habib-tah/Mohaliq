package com.habib.mohaliq.feature.history

import com.habib.mohaliq.feature.history.model.HistoryItem
import com.habib.mohaliq.feature.history.model.HistoryStatus

data class HistoryUiState(
    val entries: List<HistoryItem> = emptyList(),
    val selectedFilter: HistoryStatus? = null,
    val selectedBottomBarIndex: Int = 1
) {

    val visibleEntries: List<HistoryItem>
        get() = if (selectedFilter == null)
            entries
        else
            entries.filter { it.status == selectedFilter }

}
