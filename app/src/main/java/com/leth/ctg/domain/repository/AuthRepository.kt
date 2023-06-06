package com.leth.ctg.domain.repository

import com.leth.ctg.domain.models.ApiResult

interface AuthRepository {

    suspend fun signIn(username: String, password: String) : ApiResult<Unit>
    suspend fun signUp(username: String, password: String) : ApiResult<Unit>
    suspend fun authenticate() : ApiResult<Unit>
}
