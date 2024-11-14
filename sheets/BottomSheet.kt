package frankkeller.example.bookstore.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frankkeller.example.bookstore.model.BookDetails
import frankkeller.example.bookstore.model.Review

@Composable
fun BookDetailsSheet(
    book: BookDetails,
    reviews: List<Review>  // List of reviews for the book
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display the book title
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Display author and publication year
        Text(
            text = "${book.author}, ${book.year}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        // Display the book description
        Text(
            text = book.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        // Thicker divider between description and reviews
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp  // Increase thickness for emphasis
        )

        // Display each review in the list without divider
        reviews.forEach { review ->
            ReviewItem(review = review)
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Display reviewer name and rating
        Text(
            text = "${review.reviewer} (${review.stars}/5)",  // e.g., "John Doe (4/5)"
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Display review text content
        Text(
            text = review.text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
