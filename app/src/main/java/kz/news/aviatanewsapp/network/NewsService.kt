package kz.news.aviatanewsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "e65ee0938a2a43ebb15923b48faed18d"

interface NewService {
    @GET("everything?q=internet&apiKey=$API_KEY")
    fun getEverythingByPage(@Query("page") page: Int,
                            @Query("pageSize") pageSize: Int)
            : Deferred<NewsResponse>

    @GET("top-headlines?q=internet&apiKey=$API_KEY")
    fun getTopHeadlinesByPage(@Query("page") page: Int,
                              @Query("pageSize") pageSize: Int)
            : Deferred<NewsResponse>
}

object Network {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val newsService = retrofit.create(NewService::class.java)
}
