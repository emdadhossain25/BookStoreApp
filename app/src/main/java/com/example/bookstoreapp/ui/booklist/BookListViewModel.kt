package com.example.bookstoreapp.ui.booklist

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstoreapp.data.model.Book
import com.example.bookstoreapp.data.repository.IBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookRepository: IBookRepository
) : ViewModel() {
    private val _bookListUIState = MutableStateFlow<BookListUiState>(BookListUiState.Loading)
    val bookListUiState = _bookListUIState.asStateFlow()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        viewModelScope.launch {
            _bookListUIState.value = BookListUiState.Loading
            bookRepository.getAllBooks().onSuccess { books ->
                _bookListUIState.value = BookListUiState.Success(books)
            }.onFailure { exception ->
                _bookListUIState.value =
                    BookListUiState.Error(exception.message ?: "Book Not found")
            }
        }
    }
}