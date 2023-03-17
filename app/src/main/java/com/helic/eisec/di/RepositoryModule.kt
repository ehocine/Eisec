package com.helic.eisec.di

import com.helic.eisec.data.database.TaskDao
import com.helic.eisec.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(taskDao: TaskDao): Repository {
        return Repository(taskDao = taskDao)
    }
}