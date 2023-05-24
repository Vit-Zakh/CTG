package com.leth.ctg.data.repository

import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingItemModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.models.toEntity
import com.leth.ctg.domain.repository.TrainingRepository
import com.leth.ctg.utils.TrainingTag
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepositoryMock @Inject constructor(
    private val trainingFormatsDao: TrainingFormatsDao,
    private val exercisesDao: ExercisesDao,
) : TrainingRepository {

    private val trainingsList = listOf(
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

    override val trainings: Flow<List<TrainingSetupModel>> =
        trainingFormatsDao.fetchEnabledFormats().map { list ->
            list.map {
                it.toDomain()
            }
        }

    override val preferences: Flow<List<TrainingSetupModel>> =
        trainingFormatsDao.fetchAllFormats().map { list ->
            list.map {
                it.toDomain()
            }
        }

    private val preferencesList = listOf(
        TrainingSetupModel(
            id = 1L,
            title = "Test Title 1",
            imageUrl = null,
            tags = listOf(TrainingTag.CHEST, TrainingTag.ARMS),
            isEnabled = true,
        ),
        TrainingSetupModel(
            id = 2L,
            title = "Test Title 2",
            imageUrl = null,
            tags = listOf(
                TrainingTag.CHEST,
                TrainingTag.ARMS,
                TrainingTag.LEGS,
                TrainingTag.STRETCHING
            ),
            isEnabled = true,
        ),
        TrainingSetupModel(
            id = 3L,
            title = "Test Title 3",
            imageUrl = null,
            tags = listOf(TrainingTag.CROSSFIT),
            isEnabled = false,
        ),
        TrainingSetupModel(
            id = 4L,
            title = "Test Title 4",
            imageUrl = null,
            tags = listOf(TrainingTag.FULL_BODY),
            isEnabled = true,
        )
    )

    override suspend fun fetchPreferences() {
        delay(300)
        trainingFormatsDao.saveFormats(preferencesList.map {
            it.toEntity()
        })
    }

    override suspend fun fetchTraining(setup: TrainingSetupModel): TrainingModel {
        delay(300)
        return TrainingModel(
            id = setup.id,
            title = setup.title,
            imageUrl = setup.imageUrl,
            exercises = listOf(
                ExerciseModel(
                    id = "ex_id_1",
                    title = "Incline Bench Press",
                    type = TrainingTag.CHEST,
                    category = "CORE,"
                ),
                ExerciseModel(
                    id = "ex_id_2",
                    title = "Bench Press",
                    type = TrainingTag.CHEST,
                    category = "CORE,"
                ),
                ExerciseModel(
                    id = "ex_id_3",
                    title = "Chest Fly",
                    type = TrainingTag.CHEST,
                    category = "AUX,"
                ),
                ExerciseModel(
                    id = "ex_id_4",
                    title = "Lat Pulldown",
                    type = TrainingTag.BACK,
                    category = "CORE,"
                ),
                ExerciseModel(
                    id = "ex_id_5",
                    title = "Bicep Curl",
                    type = TrainingTag.ARMS,
                    category = "AUX,"
                ),
                ExerciseModel(
                    id = "ex_id_6",
                    title = "Triceps pushdown",
                    type = TrainingTag.ARMS,
                    category = "AUX,"
                ),
            )
        )
    }

    override suspend fun addNewTraining() = trainingFormatsDao
        .addFormat(
            TrainingFormatEntity(
                title = "",
                imageUrl = null,
                trainingTags = emptyList(),
                isEnabled = false,
            )
        )

    override suspend fun updateTrainingDetails(training: TrainingSetupModel) = trainingFormatsDao.update(
        training.toEntity()
    )

    override suspend fun regenerateExercise(exercise: ExerciseModel) {
        exercisesDao.regenerateExercise(
//            type = exercise.type,
            category = exercise.category,
            ignoredIds = listOf()
        )
    }
}