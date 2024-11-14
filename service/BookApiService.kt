package frankkeller.example.bookstore.service

import frankkeller.example.bookstore.model.BookDetails
import frankkeller.example.bookstore.model.BookDto
import frankkeller.example.bookstore.model.Review
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface BookApiService {

        @GET("list")
        fun getAllBooks(): Call<Set<BookDto>>

        @GET("details/{bookID}")
        fun getBookDetails(@Path("bookID") bookID: Int): Call<BookDetails>

        @GET("reviews/{bookID}")
        fun getBookReviews(@Path("bookID") bookID: Int): Call<Set<Review>>
}
