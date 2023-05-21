package com.leth.ctg.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.leth.ctg.DATABASE_NAME
import com.leth.ctg.data.database.Converters
import com.leth.ctg.data.database.TrainingsDatabase
import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.repository.TrainingRepositoryMock
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun provideConverters(gson: Gson): Converters = Converters(gson)

    @Provides
    @Singleton
    fun provideTrainingsDatabase(
        @ApplicationContext appContext: Context,
        gson: Gson,
    ): TrainingsDatabase =
        Room.databaseBuilder(
            appContext,
            TrainingsDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addTypeConverter(Converters(gson))
            .build()

    @Provides
    @Singleton
    fun provideTrainingFormatsDao(database: TrainingsDatabase): TrainingFormatsDao =
        database.formatsDao()

    @Provides
    @Singleton
    fun provideExercisesDao(database: TrainingsDatabase): ExercisesDao =
        database.exercisesDao()

    @Provides
    @Singleton
    fun provideTrainingsRepository(
        trainingFormatsDao: TrainingFormatsDao,
    ): TrainingRepository = TrainingRepositoryMock(trainingFormatsDao)
}
