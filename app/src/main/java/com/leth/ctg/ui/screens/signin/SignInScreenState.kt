package com.leth.ctg.ui.screens.signin

data class SignInScreenState(
    val username: String = "",
    val password: String = "",
    val usernameError: String = "",
    val passwordError: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
)
