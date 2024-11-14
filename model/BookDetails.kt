package frankkeller.example.bookstore.model

data class BookDetails(
    val id: Int,
    val author: String,
    val title: String,
    val year: Int,
    val type: String,
    val publisher: String,
    val language: String,
    val isbn13: String,
    val pages: Int,
    val description: String
)
