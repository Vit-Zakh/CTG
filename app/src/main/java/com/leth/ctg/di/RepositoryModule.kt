package com.leth.ctg.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.leth.ctg.DATABASE_NAME
import com.leth.ctg.SHARED_PREFERENCES_NAME
import com.leth.ctg.data.api.AuthApi
import com.leth.ctg.data.api.PreferencesApi
import com.leth.ctg.data.api.TrainingsApi
import com.leth.ctg.data.database.Converters
import com.leth.ctg.data.database.TrainingsDatabase
import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.LocalPreferencesDao
import com.leth.ctg.data.database.dao.PatternsDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.repository.AuthRepositoryImpl
import com.leth.ctg.data.repository.PreferencesManager
import com.leth.ctg.data.repository.TrainingsRepositoryImpl
import com.leth.ctg.data.repository.UserPreferencesRepositoryImpl
import com.leth.ctg.domain.repository.AuthRepository
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE
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
    fun provideLocalPreferencesDao(database: TrainingsDatabase): LocalPreferencesDao =
        database.localPreferencesDao()



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

    @Provides
    @Singleton
    fun provideTrainingsApi(): TrainingsApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.43:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrainingsBERepository(
        api: TrainingsApi,
        sharedPreferences: Preferences,
        trainingsDao: TrainingsDao,
        trainingFormatsDao: TrainingFormatsDao,
        localPreferencesDao: LocalPreferencesDao,
    ): TrainingsRepositoryBE = TrainingsRepositoryImpl(api, sharedPreferences, trainingsDao, trainingFormatsDao, localPreferencesDao,)

    @Provides
    @Singleton
    fun providePreferencesApi(): PreferencesApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.43:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepositoryBE(
        api: PreferencesApi,
        sharedPreferences: Preferences,
        trainingFormatsDao: TrainingFormatsDao,
        localPreferencesDao: LocalPreferencesDao,
    ): UserPreferencesRepositoryBE = UserPreferencesRepositoryImpl(api, sharedPreferences,trainingFormatsDao, localPreferencesDao)
}
