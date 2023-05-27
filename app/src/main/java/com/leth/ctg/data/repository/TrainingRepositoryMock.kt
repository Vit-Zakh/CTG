package com.leth.ctg.data.repository

import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.TrainingEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.ExercisesSetPattern
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.models.toEntity
import com.leth.ctg.domain.repository.TrainingRepository
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepositoryMock @Inject constructor(
    private val trainingFormatsDao: TrainingFormatsDao,
    private val exercisesDao: ExercisesDao,
    private val trainingsDao: TrainingsDao,
) : TrainingRepository {

    override val trainings: Flow<List<TrainingModel>> = trainingsDao.fetchTrainings().map { list ->
        list.map { it.toDomain() }
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
            tags = listOf(TrainingType.CHEST, TrainingType.ARMS),
            isEnabled = true,
        ),
        TrainingSetupModel(
            id = 2L,
            title = "Test Title 2",
            imageUrl = null,
            tags = listOf(
                TrainingType.CHEST,
                TrainingType.ARMS,
                TrainingType.LEGS,
                TrainingType.STRETCHING
            ),
            isEnabled = true,
        ),
        TrainingSetupModel(
            id = 3L,
            title = "Test Title 3",
            imageUrl = null,
            tags = listOf(TrainingType.CROSSFIT),
            isEnabled = false,
        ),
        TrainingSetupModel(
            id = 4L,
            title = "Test Title 4",
            imageUrl = null,
            tags = listOf(TrainingType.FULL_BODY),
            isEnabled = true,
        )
    )

    override suspend fun fetchPreferences() {
        delay(300)
        trainingFormatsDao.saveFormats(preferencesList.map {
            it.toEntity()
        })
    }

    override suspend fun fetchExercises() {
        exercisesDao.saveExercises(exercisesMockList.map {
            it.toEntity()
        })
    }

    override suspend fun fetchTrainings() {
        trainingFormatsDao.fetchEnabledFormats().map { preference ->
            val trainingsList = mutableListOf<TrainingEntity>()
            val generatedPattern = generateTrainingPattern(preference)
            val exercisesList = mutableListOf<ExerciseEntity>()
            generatedPattern.forEach { pattern ->
                exercisesList.addAll(
                    exercisesDao.generateExercises(
                        type = pattern.type,
                        category = pattern.category,
                        amount = pattern.amount,
                    )
                )
                trainingsList.add(
                    TrainingEntity(
                        id = preference.id,
                        title = preference.title,
                        imageUrl = preference.imageUrl,
                        exercises = exercisesList
                    )
                )
            }
            trainingsDao.saveTrainings(trainingsList)
        }
    }

    override suspend fun fetchTraining(id: Long): TrainingModel {
        delay(300)
        return trainingsDao.fetchTraining(id).toDomain()
    }

    override suspend fun addNewTraining() = trainingFormatsDao
        .addFormat(
            TrainingFormatEntity(
                title = "",
                imageUrl = null,
                trainingTypes = emptyList(),
                isEnabled = false,
            )
        )

    override suspend fun updateTrainingDetails(training: TrainingSetupModel) =
        trainingFormatsDao.update(
            training.toEntity()
        )

    override suspend fun regenerateExercise(exercise: ExerciseModel) {
        exercisesDao.regenerateExercise(
            type = exercise.type,
            category = exercise.category,
            ignoredIds = listOf()
        )
    }

    override fun generateTrainingPattern(setup: TrainingFormatEntity): List<ExercisesSetPattern> {
        val pattern = mutableListOf<ExercisesSetPattern>()
        with(setup.trainingTypes) {
            val includesStretching = contains(TrainingType.STRETCHING)
            val isSurpriseEnabled = contains(TrainingType.SURPRISE)
            when {
                this.contains(TrainingType.FULL_BODY) -> {}
                this.contains(TrainingType.CROSSFIT) -> {}
                else -> {}
            }
        }
        pattern.addAll(
            listOf(
                ExercisesSetPattern(
                    type = TrainingType.CHEST,
                    category = ExerciseClass.PRIMARY,
                    amount = 2,
                ),
                ExercisesSetPattern(
                    type = TrainingType.CHEST,
                    category = ExerciseClass.AUXILIARY,
                    amount = 2,
                ),
                ExercisesSetPattern(
                    type = TrainingType.ARMS,
                    category = ExerciseClass.PRIMARY,
                    amount = 2,
                ),
                ExercisesSetPattern(
                    type = TrainingType.BACK,
                    category = ExerciseClass.AUXILIARY,
                    amount = 2,
                ),
            )
        )
        return pattern
    }
}

val exercisesMockList = listOf(
    ExerciseModel(
        id = 1231L,
        title = "Incline Bench Press 1",
        type = TrainingType.CHEST,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 1232L,
        title = "Bench Press 1",
        type = TrainingType.CHEST,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 1233L,
        title = "Chest Fly 1",
        type = TrainingType.CHEST,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 1234L,
        title = "Incline Bench Press 2",
        type = TrainingType.CHEST,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 1235L,
        title = "Bench Press 2",
        type = TrainingType.CHEST,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 1236L,
        title = "Chest Fly 2",
        type = TrainingType.CHEST,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 1237L,
        title = "Incline Bench Press 3",
        type = TrainingType.CHEST,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 1238L,
        title = "Bench Press 3",
        type = TrainingType.CHEST,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 1239L,
        title = "Chest Fly 3",
        type = TrainingType.CHEST,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 12310L,
        title = "Lat Pulldown 1",
        type = TrainingType.BACK,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 12311L,
        title = "Lat Pulldown 2",
        type = TrainingType.BACK,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 12312L,
        title = "Lat Pulldown 3",
        type = TrainingType.BACK,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 12313L,
        title = "Lat Pulldown 4",
        type = TrainingType.BACK,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 12314L,
        title = "Bicep Curl 1",
        type = TrainingType.ARMS,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 12315L,
        title = "Triceps pushdown 1",
        type = TrainingType.ARMS,
        category = ExerciseClass.PRIMARY
    ),
    ExerciseModel(
        id = 12316L,
        title = "Bicep Curl 2",
        type = TrainingType.ARMS,
        category = ExerciseClass.AUXILIARY
    ),
    ExerciseModel(
        id = 12317L,
        title = "Triceps pushdown 2",
        type = TrainingType.ARMS,
        category = ExerciseClass.AUXILIARY
    ),
)