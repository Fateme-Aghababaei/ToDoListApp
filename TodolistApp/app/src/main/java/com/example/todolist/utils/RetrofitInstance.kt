package com.example.todolist.utils

import com.example.todolist.data.APIInterface
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Singleton object for providing a Retrofit API instance.
 */
object RetrofitInstance {

    /**
     * Lazily initialized Retrofit API instance.
     */
    val api: APIInterface by lazy {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES) // write timeout
            .readTimeout(2, TimeUnit.MINUTES) // read timeout
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(Util.Base)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient) // Set the OkHttpClient with the interceptor
            .build()
            .create(APIInterface::class.java)
    }
}
