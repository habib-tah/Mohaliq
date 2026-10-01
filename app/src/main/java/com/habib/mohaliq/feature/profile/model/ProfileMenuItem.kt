package com.habib.mohaliq.feature.profile.model

data class ProfileMenuItem(
    val id: String,
    val labelRes: Int,
    val icon: Int,
    val isDestructive: Boolean = false
)
