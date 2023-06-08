package com.leth.ctg.data.api

import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.data.requests.SavePreferencesRequest
import com.leth.ctg.data.response.ResponseWithData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PreferencesApi {
    @POST("save_preferences")
    suspend fun savePreferences(
        @Header("Authorization") token: String,
        @Body request: SavePreferencesRequest,
    )

    @GET("preferences")
    suspend fun fetchPreferences(
        @Header("Authorization") token: String,
    ) : ResponseWithData<List<PreferenceDto>>
}
