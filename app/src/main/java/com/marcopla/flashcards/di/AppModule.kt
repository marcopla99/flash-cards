package com.marcopla.flashcards.di

import android.app.Application
import androidx.room.Room
import com.marcopla.flashcards.data.datasource.FlashCardDatabase
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFlashCardDatabase(application: Application): FlashCardDatabase {
        return Room.databaseBuilder(
            application,
            FlashCardDatabase::class.java,
            "flash_card_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlashCardRepository(db: FlashCardDatabase): FlashCardRepository {
        return FlashCardRepositoryImpl(db.flashCardDao)
    }
}