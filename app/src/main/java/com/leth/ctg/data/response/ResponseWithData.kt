package com.leth.ctg.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithData<T>(val data: T)
