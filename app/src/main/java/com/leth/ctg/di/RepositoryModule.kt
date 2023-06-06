package com.leth.ctg.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.leth.ctg.DATABASE_NAME
import com.leth.ctg.SHARED_PREFERENCES_NAME
import com.leth.ctg.data.api.AuthApi
import com.leth.ctg.data.database.Converters
import com.leth.ctg.data.database.TrainingsDatabase
import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.PatternsDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.repository.AuthRepositoryImpl
import com.leth.ctg.data.repository.PreferencesManager
import com.leth.ctg.data.repository.TrainingRepositoryMock
import com.leth.ctg.domain.repository.AuthRepository
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    fun provideTrainingsDao(database: TrainingsDatabase): TrainingsDao =
        database.trainingsDao()

    @Provides
    @Singleton
    fun providePatternsDao(database: TrainingsDatabase): PatternsDao =
        database.patternsDao()

    @Provides
    @Singleton
    fun provideTrainingsRepository(
        trainingFormatsDao: TrainingFormatsDao,
        exercisesDao: ExercisesDao,
        trainingsDao: TrainingsDao,
        patternsDao: PatternsDao,
    ): TrainingRepository = TrainingRepositoryMock(trainingFormatsDao, exercisesDao, trainingsDao, patternsDao)

    @Provides
    @Singleton
    fun provideAuthorizationRepository(
        api: AuthApi,
        preferences: Preferences,
    ): AuthRepository = AuthRepositoryImpl(api, preferences,)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): Preferences =
        PreferencesManager(
            appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.43:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}
