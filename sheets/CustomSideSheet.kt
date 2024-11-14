package frankkeller.example.bookstore.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSideSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    // Display a background overlay (scrim) when the side sheet is visible
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))  // Semi-transparent black overlay
                .clickable { onDismiss() }  // Closes the sheet when clicked
        )
    }

    // Animated side sheet that slides in from the left
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -it }), // Slide in from the left
        exit = slideOutHorizontally(targetOffsetX = { -it })   // Slide out to the left
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()          // Makes the sheet full height
                .width(300.dp)            // Fixed width of 300.dp; adjust as needed
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),          // Padding around the content inside the sheet
            tonalElevation = 8.dp         // Elevation for slight shadow effect
        ) {
            content()  // Displays the passed content inside the sheet
        }
    }
}
