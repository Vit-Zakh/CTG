package com.leth.ctg.domain.repository

interface Preferences {
    fun saveToken(value: String)
    fun getToken(): String?
}
