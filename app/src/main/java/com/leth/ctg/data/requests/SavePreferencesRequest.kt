package com.leth.ctg.data.requests

import com.leth.ctg.data.dto.PreferenceDto

data class SavePreferencesRequest(
    val preferencesList: List<PreferenceDto>
)
