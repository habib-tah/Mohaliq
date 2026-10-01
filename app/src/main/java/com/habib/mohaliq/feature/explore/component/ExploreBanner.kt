package com.habib.mohaliq.feature.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.PromotionBanner

private const val BANNER_IMAGE_URL =
    "https://images.unsplash.com/photo-1590476490365-e1a8e952fee4?w=1200&q=85&auto=format&fit=crop"

@Composable
fun ExploreBanner(
    banner: PromotionBanner,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 7f)
            .clip(RoundedCornerShape(Dimens.Radius24))
    ) {

        // Full-bleed background image
        AsyncImage(
            model = BANNER_IMAGE_URL,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark gradient for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.78f),
                            Color.Black.copy(alpha = 0.38f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Banner text
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.68f)
                .padding(Dimens.Space20)
        ) {
            Text(
                text = banner.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Text(
                text = banner.subtitle,
                modifier = Modifier.padding(top = Dimens.Space8),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.92f)
            )
        }
    }
}