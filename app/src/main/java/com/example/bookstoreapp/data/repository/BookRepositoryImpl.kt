package com.example.bookstoreapp.data.repository

import androidx.collection.emptyIntSet
import com.example.bookstoreapp.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor() : IBookRepository {

    val favoriteBooks = mutableSetOf<String>() // in memory storage for favorite books

    private val mockBooks = listOf(
        Book(
            id = "1",
            title = "The Midnight Library",
            author = "Matt Haig",
            coverUrl = "https://picsum.photos/seed/book1/200/300",
            description = "Between life and death there is a library, and within that library, the shelves go on forever. Every book provides a chance to try another life you could have lived. To see how things would be if you had made other choices... Would you have done anything different, if you had the chance to undo your regrets?",
            rating = 4.5f,
            price = 12.99,
            publicationYear = 2020,
            isbn = "978-0525559474"
        ),
        Book(
            id = "2",
            title = "Project Hail Mary",
            author = "Andy Weir",
            coverUrl = "https://picsum.photos/seed/book2/200/300",
            description = "A lone astronaut must save the earth from disaster in this incredible new science-based thriller from the author of The Martian. Ryland Grace is the sole survivor on a desperate, last-chance mission—and if he fails, humanity and the earth itself will perish.",
            rating = 4.8f,
            price = 14.99,
            publicationYear = 2021,
            isbn = "978-0593135204"
        ),
        Book(
            id = "3",
            title = "Atomic Habits",
            author = "James Clear",
            coverUrl = "https://picsum.photos/seed/book3/200/300",
            description = "An Easy & Proven Way to Build Good Habits & Break Bad Ones. No matter your goals, Atomic Habits offers a proven framework for improving--every day.",
            rating = 4.7f,
            price = 16.99,
            publicationYear = 2018,
            isbn = "978-0735211292"
        ),
        Book(
            id = "4",
            title = "The Thursday Murder Club",
            author = "Richard Osman",
            coverUrl = "https://picsum.photos/seed/book4/200/300",
            description = "In a peaceful retirement village, four unlikely friends meet weekly in the Junk Room to discuss unsolved murders. But when a local developer is found dead, the Thursday Murder Club find themselves in the middle of their first live case.",
            rating = 4.3f,
            price = 13.99,
            publicationYear = 2020,
            isbn = "978-1984880987"
        ),
        Book(
            id = "5",
            title = "Educated",
            author = "Tara Westover",
            coverUrl = "https://picsum.photos/seed/book5/200/300",
            description = "A memoir about a young girl who, kept out of school, leaves her survivalist family and goes on to earn a PhD from Cambridge University.",
            rating = 4.6f,
            price = 15.99,
            publicationYear = 2018,
            isbn = "978-0399590504"
        ),
        Book(
            id = "6",
            title = "The Silent Patient",
            author = "Alex Michaelides",
            coverUrl = "https://picsum.photos/seed/book6/200/300",
            description = "Alicia Berenson's life is seemingly perfect. But one evening, she shoots her husband and then never speaks another word. A criminal psychotherapist becomes obsessed with uncovering her motive.",
            rating = 4.2f,
            price = 11.99,
            publicationYear = 2019,
            isbn = "978-1250301697"
        ),
        Book(
            id = "7",
            title = "Sapiens",
            author = "Yuval Noah Harari",
            coverUrl = "https://picsum.photos/seed/book7/200/300",
            description = "A Brief History of Humankind. From examining the role evolving humans have played in the global ecosystem to charting the rise of empires, Sapiens integrates history and science to reconsider accepted narratives.",
            rating = 4.5f,
            price = 17.99,
            publicationYear = 2011,
            isbn = "978-0062316097"
        ),
        Book(
            id = "8",
            title = "Where the Crawdads Sing",
            author = "Delia Owens",
            coverUrl = "https://picsum.photos/seed/book8/200/300",
            description = "For years, rumors of the 'Marsh Girl' have haunted Barkley Cove. So in late 1969, when handsome Chase Andrews is found dead, the locals immediately suspect Kya Clark, the so-called Marsh Girl.",
            rating = 4.4f,
            price = 14.49,
            publicationYear = 2018,
            isbn = "978-0735219090"
        ),
        Book(
            id = "9",
            title = "The Power of Now",
            author = "Eckhart Tolle",
            coverUrl = "https://picsum.photos/seed/book9/200/300",
            description = "A Guide to Spiritual Enlightenment. The Power of Now shows you that every minute you spend worrying about the future or regretting the past is a minute lost.",
            rating = 4.1f,
            price = 12.49,
            publicationYear = 1997,
            isbn = "978-1577314806"
        ),
        Book(
            id = "10",
            title = "Becoming",
            author = "Michelle Obama",
            coverUrl = "https://picsum.photos/seed/book10/200/300",
            description = "In her memoir, a work of deep reflection and mesmerizing storytelling, Michelle Obama invites readers into her world, chronicling the experiences that have shaped her.",
            rating = 4.7f,
            price = 18.99,
            publicationYear = 2018,
            isbn = "978-1524763138"
        )
    )

    override suspend fun getAllBooks(): Result<List<Book>> {
        try {
            val booksWithFavorites = mockBooks.map { book ->
                book.copy(isFavorite = favoriteBooks.contains(book.id))
            }
            return Result.success(booksWithFavorites)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getBook(bookId: String): Result<Book> {
        try {
            val book = mockBooks.find { book -> bookId == book.id }
            return if (book != null) {
                val updateBook = book.copy(isFavorite = favoriteBooks.contains(book.id))
                Result.success(updateBook)
            } else {
                Result.failure(Exception("Book not found"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(bookId: String): Result<Boolean> {
        try {
            delay(200)

            val isFavorite = if (favoriteBooks.contains(bookId)) {
                favoriteBooks.remove(bookId)
                false
            } else {
                favoriteBooks.add(bookId)
                true
            }
            return Result.success(isFavorite)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun searchBooks(query: String): Flow<List<Book>> = flow {
        val filteredBooks = if (query.isBlank()) {
            mockBooks
        } else {
            mockBooks.filter { book ->
                book.title.contains(query, true) || book.author.contains(
                    query,
                    true
                )
            }
        }

        val booksWithFavorites = filteredBooks.map { book ->
            book.copy(isFavorite = favoriteBooks.contains(book.id))
        }

        emit(booksWithFavorites)
    }
}