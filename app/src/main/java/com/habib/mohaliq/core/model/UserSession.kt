package com.habib.mohaliq.core.model

data class UserSession(
    val isLoggedIn: Boolean,
    val name: String,
    val email: String
)
