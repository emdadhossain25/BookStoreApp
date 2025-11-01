package com.example.bookstoreapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookstoreapp.ui.bookdetail.BookDetailScreen
import com.example.bookstoreapp.ui.booklist.BookList

sealed class Screen(val route: String) {
    data object BookList : Screen("book_list")
    data object BookDetails : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: String) = "book_detail/$bookId"
    }
}


@Composable
fun NavGraph(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.BookList.route
    ) {

        // Book List Screen
        composable(route = Screen.BookList.route) {
            BookList(
                onBookClick = { bookId ->
                    navHostController.navigate(Screen.BookDetails.createRoute(bookId))
                }
            )
        }


        composable(
            route = Screen.BookDetails.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )) {
            BookDetailScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

    }

}