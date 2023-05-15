package com.leth.ctg.data.repository

import com.leth.ctg.domain.models.TrainingItemModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.TrainingRepository
import kotlinx.coroutines.delay

class TrainingRepositoryMock : TrainingRepository {

    private val trainings = listOf(
        TrainingItemModel(
            id = "test_id_1",
            title = "Test Title 1",
            imageUrl = "https://images.unsplash.com/photo-1681491313239-9d0033095fde?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1335&q=80"
        ),
        TrainingItemModel(
            id = "test_id_2",
            title = "Test Title 2",
            imageUrl = "https://images.unsplash.com/photo-1677764110182-6fcbec19f41b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=927&q=80"
        ),
        TrainingItemModel(
            id = "test_id_3",
            title = "Test Title 3",
            imageUrl = "https://images.unsplash.com/photo-1584464457692-54516d705fe0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDM4fEJuLURqcmNCcndvfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=900&q=60"
        ),
    )

    private val preferencesList = listOf(
        TrainingSetupModel(
            id = "test_id_1",
            title = "Test Title 1",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_2",
            title = "Test Title 2",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_3",
            title = "Test Title 3",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_4",
            title = "Test Title 4",
            imageUrl = null,
            tags = emptyList()
        )
    )

    override suspend fun fetchTrainings(): List<TrainingItemModel> {
        delay(300)
        return trainings
    }

    override suspend fun fetchPreferences(): List<TrainingSetupModel> {
        delay(300)
        return preferencesList
    }
}