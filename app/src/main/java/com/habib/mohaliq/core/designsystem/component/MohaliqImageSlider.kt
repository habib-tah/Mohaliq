package com.habib.mohaliq.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.theme.Dimens

/**
 * Shared image slider used by any detail screen with a hero gallery
 * (Hotel Detail, Restaurant Detail, Destination Detail). Falls back to a
 * placeholder panel if [imageUrls] is empty (e.g. data hasn't loaded yet).
 *
 * [overlay] is for screen-specific controls (back button, favorite button)
 * that sit on top of the slider.
 */
@Composable
fun MohaliqImageSlider(
    modifier: Modifier = Modifier,
    imageUrls: List<String> = emptyList(),
    sliderHeight: androidx.compose.ui.unit.Dp = 340.dp,
    overlay: @Composable BoxScope.() -> Unit = {}
) {

    val pageCount = imageUrls.size.coerceAtLeast(1)
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(sliderHeight)
            .clip(RoundedCornerShape(bottomStart = Dimens.Radius24, bottomEnd = Dimens.Radius24))
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val url = imageUrls.getOrNull(page)

            if (url != null) {

                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            } else {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f))
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_gallery_edit),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

            }

        }

        if (pageCount > 1) {

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Dimens.Space16)
            ) {

                repeat(pageCount) { index ->

                    val selected = pagerState.currentPage == index

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (selected) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (selected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = .3f)
                            )
                    )

                }

            }

        }

        overlay()

    }

}
