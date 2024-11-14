package frankkeller.example.bookstore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import frankkeller.example.bookstore.model.BookDto


@Composable
fun BookItem(
    book: BookDto,  // Accepts BookDto object to display basic book info
    color: Color,   // Sets background color of the card
    modifier: Modifier = Modifier,
    onClick: () -> Unit  // Callback function for click actions
) {
    Card(
        modifier = modifier
            .size(width = 100.dp, height = 150.dp)  // Fixed size for each book card
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(5.dp),  // Rounded corners for aesthetic appeal
        elevation = CardDefaults.cardElevation(5.dp),  // Subtle elevation for shadow effect
        colors = CardDefaults.cardColors(containerColor = color) // Background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Displays the book title with ellipsis for long titles
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                softWrap = true
            )
            Spacer(modifier = Modifier.height(4.dp))  // Spacer between title and author
            // Displays the book author with ellipsis for long names
            Text(
                text = "by ${book.author}",  // Shows author
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.5f),
            )
        }
    }
}
