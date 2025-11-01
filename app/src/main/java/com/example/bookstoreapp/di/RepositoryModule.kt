package com.example.bookstoreapp.di

import com.example.bookstoreapp.data.repository.BookRepositoryImpl
import com.example.bookstoreapp.data.repository.IBookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): IBookRepository
}