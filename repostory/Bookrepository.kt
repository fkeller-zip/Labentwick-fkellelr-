package frankkeller.example.bookstore.repostory

import android.os.Build
import frankkeller.example.bookstore.service.BookApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Bookrepository {

    private const val BASE_URL = "http://10.0.2.2:80/"

    val api: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Parses JSON with Gson
            .build()
            .create(BookApiService::class.java)  // Creates an implementation of the API interface
    }

    private fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk" == Build.PRODUCT)
    }
}
