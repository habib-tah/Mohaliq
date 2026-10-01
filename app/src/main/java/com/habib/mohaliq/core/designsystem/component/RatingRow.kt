package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.designsystem.theme.RatingStar

/**
 * Shared star + rating + review-count row. Used anywhere a rating is shown:
 * Home/Explore travel cards, Hotel detail, Restaurant detail, Booking rooms.
 */
@Composable
fun RatingRow(
    rating: Float,
    reviewCount: Int?,
    modifier: Modifier = Modifier,
    iconSize: Dp = Dimens.Space16,
    showReviewCount: Boolean = true
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = null,
            tint = RatingStar,
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = if (showReviewCount && reviewCount != null) "$rating ($reviewCount)" else "$rating",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = Dimens.Space4)
        )

    }

}
