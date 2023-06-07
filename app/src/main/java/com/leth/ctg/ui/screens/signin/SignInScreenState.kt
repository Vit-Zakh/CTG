package com.leth.ctg.ui.screens.signin

data class SignInScreenState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
)