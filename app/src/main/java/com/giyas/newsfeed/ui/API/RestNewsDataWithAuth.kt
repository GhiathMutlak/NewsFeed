package com.giyas.newsfeed.ui.API

import android.content.Context
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class RestNewsDataWithAuth {

    companion object {
        private var gitApiInterface: GitApiInterface? = null

        fun getArticle(): GitApiInterface? {
            if (gitApiInterface == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val builder = OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                builder.addInterceptor(interceptor)
//                val interceptor1: Interceptor = object : Interceptor {
//                    @Throws(IOException::class)
//                    override fun intercept(chain: Interceptor.Chain): Response {
//                        val original = chain.request()
//                        // Request customization: add request headers
//                        val requestBuilder = original.newBuilder()
//                        requestBuilder.addHeader("Content-Type", "application/json")
//                        requestBuilder.addHeader("Accept", "application/json")
//                        val request = requestBuilder.build()
//                        return chain.proceed(request)
//                    }
//                }
//                builder.interceptors().add(interceptor1)
                val okHttpClient = builder.build()
                val client = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                gitApiInterface = client.create(GitApiInterface::class.java)
            }
            return gitApiInterface
        }
    }

    interface GitApiInterface {
        @GET("/v2/everything")
        fun getNewsData(@Query("q") param1:String ,
                        @Query("apiKey") param2:String ): Call<NewsData>?
    }
}