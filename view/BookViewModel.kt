package frankkeller.example.bookstore.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import frankkeller.example.bookstore.model.BookDetails
import frankkeller.example.bookstore.model.BookDto
import frankkeller.example.bookstore.repostory.Bookrepository
import frankkeller.example.bookstore.model.Review
import kotlinx.coroutines.launch
import retrofit2.await

class BookViewModel : ViewModel() {

    // Holds minimal book data for displaying on the main screen
    private val _allBooks = mutableStateOf<Set<BookDto>>(emptySet())
    val allBooks: State<Set<BookDto>> = _allBooks  // Publicly accessible read-only state

    // Holds detailed information for a selected book
    private val _bookDetails = mutableStateOf<BookDetails?>(null)
    val bookDetails: State<BookDetails?> = _bookDetails  // Public read-only state

    // Holds reviews for a selected book
    private val _bookReviews = mutableStateOf<Set<Review>>(emptySet())
    val bookReviews: State<Set<Review>> = _bookReviews  // Public read-only state

    fun fetchAllBooks() {
        viewModelScope.launch {
            try {
                val response = Bookrepository.api.getAllBooks().await()
                _allBooks.value = response
                println("Books loaded: ${response.size}") // Log the number of books loaded
            } catch (e: Exception) {
                println("Error loading books: ${e.message}")  // Log error message
            }
        }
    }

    fun fetchBookDetails(bookID: Int) {
        viewModelScope.launch {
            try {
                val response = Bookrepository.api.getBookDetails(bookID).await()
                _bookDetails.value = response
            } catch (e: Exception) {
                println("Error fetching book details: ${e.message}") // Handle and log errors
            }
        }
    }

    fun fetchBookReviews(bookID: Int) {
        viewModelScope.launch {
            try {
                val response = Bookrepository.api.getBookReviews(bookID).await()
                _bookReviews.value = response
            } catch (e: Exception) {
                println("Error fetching book reviews: ${e.message}") // Handle and log errors
            }
        }
    }
}
