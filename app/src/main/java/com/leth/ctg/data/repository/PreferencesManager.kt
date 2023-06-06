package com.leth.ctg.data.repository

import android.content.SharedPreferences
import com.leth.ctg.domain.repository.Preferences

private const val TOKEN_KEY = "token_key"

class PreferencesManager(private val sharedPreferences: SharedPreferences) : Preferences {
    override fun saveToken(value: String) =
        sharedPreferences.edit().putString(TOKEN_KEY, value).apply()


    override fun getToken(): String? =
        sharedPreferences.getString(TOKEN_KEY, null)
}
