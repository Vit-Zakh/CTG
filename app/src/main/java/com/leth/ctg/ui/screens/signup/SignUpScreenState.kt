package com.leth.ctg.ui.screens.signup

data class SignUpScreenState(
    val username: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val showPassword: Boolean = false,
    val showRepeatPassword: Boolean = false,
    val isLoading: Boolean = false,
)