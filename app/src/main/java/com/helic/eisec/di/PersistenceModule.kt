package com.helic.eisec.di

import android.content.Context
import androidx.room.Room
import com.helic.eisec.data.database.EisecDatabase
import com.helic.eisec.data.database.TaskDao
import com.helic.eisec.data.datastore.ThemeManager
import com.helic.eisec.data.datastore.ThemeManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): ThemeManager {
        return ThemeManagerImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: EisecDatabase): TaskDao = database.getTaskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): EisecDatabase =
        Room.databaseBuilder(
            context,
            EisecDatabase::class.java,
            "task-db"
        ).fallbackToDestructiveMigration().build()
}