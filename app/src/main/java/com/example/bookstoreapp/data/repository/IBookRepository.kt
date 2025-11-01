package com.example.bookstoreapp.data.repository

import com.example.bookstoreapp.data.model.Book
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun getAllBooks(): Result<List<Book>>
    suspend fun getBook(bookId: String): Result<Book>
    suspend fun toggleFavorite(bookId: String): Result<Boolean>
    fun searchBooks(query: String): Flow<List<Book>>
}