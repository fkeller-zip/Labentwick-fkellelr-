package frankkeller.example.bookstore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.Manifest

@Composable
fun SettingsScreen(context: Context) {
    var isChecked by remember { mutableStateOf(false) }  // State for checkbox
    val coroutineScope = rememberCoroutineScope()

    Log.d("SettingsScreen", "Checkbox checked: $isChecked")  // Debug log for checkbox state

    // Launcher to request notification permission on Android 13+
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                coroutineScope.launch {
                    Log.d("SettingsScreen", "Permission granted. Launching delayed notification.")
                    delay(5000)  // Wait for 5 seconds before showing notification
                    showNotification(context)
                }
            } else {
                Log.d("SettingsScreen", "Permission denied.")
            }
        }
    )

    // Ensure notification channel is created when the composable is launched
    LaunchedEffect(Unit) {
        createNotificationChannel(context)
    }

    // Trigger notification if the checkbox is checked, after a 5-second delay
    LaunchedEffect(isChecked) {
        if (isChecked) {
            Log.d("SettingsScreen", "isChecked is true. Checking permissions.")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Request permission on Android 13+
                if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    coroutineScope.launch {
                        Log.d("SettingsScreen", "Permission already granted. Launching delayed notification.")
                        delay(5000)
                        showNotification(context)
                    }
                } else {
                    Log.d("SettingsScreen", "Permission not granted. Launching permission request.")
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // Trigger notification directly on Android 12 and below
                coroutineScope.launch {
                    Log.d("SettingsScreen", "Android version below 13. Launching delayed notification.")
                    delay(5000)
                    showNotification(context)
                }
            }
        }
    }

    // Layout for the settings screen with a checkbox for enabling notifications
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Enable Notification After 5 Seconds")
        Spacer(modifier = Modifier.width(8.dp))
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Settings Channel"
        val descriptionText = "Channel for settings notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("settings_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showNotification(context: Context) {
    try {
        // Check if notification permission is granted on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!permissionGranted) {
                Log.d("showNotification", "Permission not granted. Skipping notification.")
                return
            }
        }

        // Build and display the notification if permission is granted
        val builder = NotificationCompat.Builder(context, "settings_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)  // Default icon for the notification
            .setContentTitle("Settings Notification")
            .setContentText("This notification was triggered by the checkbox.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(101, builder.build())  // Show the notification with ID 101
        }
        Log.d("showNotification", "Notification displayed.")
    } catch (e: SecurityException) {
        Log.e("showNotification", "SecurityException while trying to post notification: ${e.message}")
    } catch (e: Exception) {
        Log.e("showNotification", "Unexpected error: ${e.message}")
    }
}
