package frankkeller.example.bookstore.model


data class Review(
    val bookId: Int,
    val reviewer: String,
    val text: String,
    val stars: Int
)
