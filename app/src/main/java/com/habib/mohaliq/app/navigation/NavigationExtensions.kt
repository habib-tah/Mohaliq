package com.habib.mohaliq.app.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(route: String) {
    navigate(route) {
        launchSingleTop = true
    }
}