package com.habib.mohaliq.feature.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.theme.Neutral900

private const val DEFAULT_ZOOM = 14f

/**
 * Displays a map centered on the selected place with a marker at its location.
 */
@Composable
fun MapRoute(
    latitude: Double,
    longitude: Double,
    title: String,
    onBack: () -> Unit = {}
) {

    val position = remember(latitude, longitude) { LatLng(latitude, longitude) }

    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition.fromLatLngZoom(position, DEFAULT_ZOOM)
    }

    MohaliqScaffold(

        topBar = {

            MohaliqTopBar(
                title = title,
                navigationIcon = R.drawable.ic_arrow_left_outline,
                onNavigationClick = onBack,
                contentColor = Neutral900
            )

        }

    ) { padding ->

        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = padding
        ) {

            Marker(
                state = rememberMarkerState(position = position),
                title = title
            )

        }

    }

}
