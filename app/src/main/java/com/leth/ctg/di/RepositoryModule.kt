package com.leth.ctg.di

import com.leth.ctg.data.repository.TrainingRepositoryMock
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTrainingsRepository(
    ): TrainingRepository = TrainingRepositoryMock()
}
