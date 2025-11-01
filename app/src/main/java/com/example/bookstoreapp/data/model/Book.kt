package com.example.bookstoreapp.data.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val description: String,
    val rating: Float, // 0.0 - 5.0
    val price: Double,
    val publicationYear: Int,
    val isbn: String,
    val isFavorite: Boolean = false
)
