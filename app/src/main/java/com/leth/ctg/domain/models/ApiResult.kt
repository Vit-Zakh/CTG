package com.leth.ctg.domain.models

sealed class ApiResult<T>(val data: T? = null, val message: String = "") {
    class Success<T>(data: T? = null) : ApiResult<T>(data = data)
    class Error<T>(message: String = "") : ApiResult<T>(message = message)
}
