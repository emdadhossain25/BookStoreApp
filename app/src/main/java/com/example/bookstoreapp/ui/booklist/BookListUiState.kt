package com.example.bookstoreapp.ui.booklist

import com.example.bookstoreapp.data.model.Book

sealed class BookListUiState {
    data object Loading : BookListUiState()
    data class Success(val books: List<Book>) : BookListUiState()
    data class Error(val message: String) : BookListUiState()
}