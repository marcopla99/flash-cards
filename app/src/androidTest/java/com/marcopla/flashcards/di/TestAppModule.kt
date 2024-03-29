package com.marcopla.flashcards.di

import android.app.Application
import androidx.room.Room
import com.marcopla.flashcards.data.datasource.FlashCardDatabase
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFlashCardDatabase(application: Application): FlashCardDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            FlashCardDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlashCardRepository(db: FlashCardDatabase): FlashCardRepository {
        return FlashCardRepositoryImpl(db.flashCardDao)
    }
}