package com.habib.mohaliq.core.designsystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MohaliqLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    dotSize: Dp = 8.dp
) {
    val transition = rememberInfiniteTransition(label = "loadingDots")

    val dot1Alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.35f at 0
                1f at 150
                0.35f at 300
                0.35f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "dot1Alpha"
    )

    val dot2Alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.35f at 0
                0.35f at 150
                1f at 300
                0.35f at 450
                0.35f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "dot2Alpha"
    )

    val dot3Alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 900
                0.35f at 0
                0.35f at 300
                1f at 450
                0.35f at 600
                0.35f at 900
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "dot3Alpha"
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LoadingDot(dot1Alpha, color, dotSize)
        LoadingDot(dot2Alpha, color, dotSize)
        LoadingDot(dot3Alpha, color, dotSize)
    }
}

@Composable
private fun LoadingDot(
    alpha: Float,
    color: Color,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .alpha(alpha)
            .background(
                color = color,
                shape = CircleShape
            )
    )
}