package me.kennyvaldivia.riway.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kennyvaldivia.riway.AppDatabase
import me.kennyvaldivia.riway.alarm.AlarmDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideAlarmDao(appDatabase: AppDatabase): AlarmDao {
        return appDatabase.alarmDao()
    }
}
