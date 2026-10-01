package com.habib.mohaliq.feature.history.model

import androidx.annotation.StringRes
import com.habib.mohaliq.R

enum class HistoryStatus(@StringRes val labelRes: Int) {
    UPCOMING(R.string.filter_upcoming),
    COMPLETED(R.string.filter_completed)
}
