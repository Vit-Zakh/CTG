package com.leth.ctg.data.repository

import com.leth.ctg.data.api.AuthApi
import com.leth.ctg.data.requests.AuthRequest
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.repository.AuthRepository
import com.leth.ctg.domain.repository.Preferences

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val preferences: Preferences,
) : AuthRepository {

    override suspend fun signUp(username: String, password: String): ApiResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            signIn(username, password)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun signIn(username: String, password: String): ApiResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            preferences.saveToken(response.token)
            ApiResult.Success()
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun authenticate(): ApiResult<Unit> {
        return try {
            val token = preferences.getToken() ?: return ApiResult.Error()
            api.authenticate("Bearer $token")
            ApiResult.Success()
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
