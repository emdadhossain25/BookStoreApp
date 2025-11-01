package com.example.bookstoreapp.ui.booklist

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstoreapp.data.model.Book
import com.example.bookstoreapp.ui.booklist.component.BookListItem

@Composable
fun BookList(
    onBookClick: (String) -> Unit,
    bookListViewModel: BookListViewModel = hiltViewModel()
) {

    val uiState by bookListViewModel.bookListUiState.collectAsState()

    when (val state = uiState) {
        is BookListUiState.Error -> {
            ErrorContent(
                message = state.message,
                onRetry = { bookListViewModel.fetchBooks() }
            )
        }

        is BookListUiState.Loading -> {

            LoadingContent()
        }

        is BookListUiState.Success -> {

            if (state.books.isEmpty()) {
                EmptyContent()
            } else {
                BookListContent(
                    books = state.books,
                    onBookClick = onBookClick,
                )
            }
        }
    }
}

@Composable
fun BookListContent(
    books: List<Book>,
    onBookClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 15.dp)
            ) {
                items(
                    items = books,
                    key = { book -> book.id }) { book ->
                    BookListItem(
                        book = book,
                        onBookClick = onBookClick,
                        onFavoriteClick = {}
                    )

                }
            }
        }

    }

}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Loading Books",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(32.dp)
        ) {

            Text(
                text = "books",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "No books found",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Try Adjusting your search or filters",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "X",
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "Oops! Something went wrong.",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text("Retry")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun LoadingContentPreview() {
    LoadingContent()
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    ErrorContent("preview for error content", onRetry = {})
}

@Preview(showBackground = true)
@Composable
private fun EmptyContentPreview() {
    EmptyContent()
}

@Preview(showBackground = true)
@Composable
private fun BookListContentPreview() {


    val mockBooks = listOf(
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
    BookListContent(
        books = mockBooks,
        onBookClick = {}
    )


}