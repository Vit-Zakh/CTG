package com.leth.ctg.data.requests

import com.leth.ctg.data.dto.TrainingDto

data class SaveTrainingsRequest(
    val trainingsList: List<TrainingDto>
)
