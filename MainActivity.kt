package frankkeller.example.bookstore

import frankkeller.example.bookstore.sheets.BookDetailsSheet
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import de.hhn.bookstore.ui.theme.BookDarkColors
import de.hhn.bookstore.ui.theme.BookLightColors
import de.hhn.bookstore.ui.theme.BookStoreTheme
import frankkeller.example.bookstore.sheets.CustomSideSheet
import frankkeller.example.bookstore.view.BookViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookStoreTheme {
                // Launch the BookStoreScreen composable, passing the application context
                BookStoreScreen(applicationContext)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun BookStoreScreen(context: Context) {
    val viewModel: BookViewModel = viewModel()  // ViewModel to manage book data
    val books = viewModel.allBooks.value.toList()  // List of all books
    var selectedBookId by remember { mutableStateOf<Int?>(null) }  // ID of the selected book
    var isBottomSheetVisible by remember { mutableStateOf(false) }  // Controls bottom sheet visibility
    var showSideSheet by remember { mutableStateOf(false) }  // Controls side sheet visibility

    // Determine color scheme based on theme (dark/light)
    val bookColors = if (isSystemInDarkTheme()) BookDarkColors else BookLightColors
    val backgroundColor = MaterialTheme.colorScheme.background

    // Fetch books when the composable is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchAllBooks()
    }

    // Main container for the screen with adaptive background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)  // Applies the background color based on theme
    ) {
        if (books.isEmpty()) {
            // Display message if no books are available
            Text("No books available. Please check your network connection.", modifier = Modifier.align(Alignment.Center))
        } else {
            // Display books in a grid format
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),  // Three columns for book items
                contentPadding = PaddingValues(16.dp)
            ) {
                items(books.size) { index ->
                    val book = books[index]
                    val color = bookColors[index % bookColors.size]  // Cycle through theme colors

                    BookItem(
                        book = book,
                        color = color,
                        onClick = {
                            // Set the selected book and show the bottom sheet
                            selectedBookId = book.id
                            isBottomSheetVisible = true
                            viewModel.fetchBookDetails(book.id)  // Fetch book details
                            viewModel.fetchBookReviews(book.id)  // Fetch reviews
                        }
                    )
                }
            }
        }

        // Bottom sheet to show book details and reviews
        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetVisible = false }
            ) {
                selectedBookId?.let { bookId ->
                    val bookDetails = viewModel.bookDetails.value
                    val bookReviews = viewModel.bookReviews.value.toList()

                    // Display book details if available, otherwise show loading message
                    if (bookDetails != null) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            BookDetailsSheet(
                                book = bookDetails,
                                reviews = bookReviews
                            )
                        }
                    } else {
                        Text("Loading book details...")
                    }
                }
            }
        }

        // Settings button in the bottom right corner to open the side sheet
        Button(
            onClick = { showSideSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Settings")
        }

        // Side sheet for settings, visible when `showSideSheet` is true
        CustomSideSheet(
            isVisible = showSideSheet,
            onDismiss = { showSideSheet = false }
        ) {
            SettingsScreen(context)
        }
    }
}
