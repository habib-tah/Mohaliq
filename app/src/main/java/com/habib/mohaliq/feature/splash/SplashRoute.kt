package com.habib.mohaliq.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqLoadingIndicator
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.designsystem.theme.Neutral50

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit = {},
    onNavigateLogin: () -> Unit = {}
) {

    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {
                SplashEvent.NavigateHome -> onNavigateHome()
                SplashEvent.NavigateLogin -> onNavigateLogin()
            }

        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(250.dp),
                tint = Neutral50
            )
        }

        MohaliqLoadingIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimens.Space32),
            color = MaterialTheme.colorScheme.onPrimary
        )

    }

}
