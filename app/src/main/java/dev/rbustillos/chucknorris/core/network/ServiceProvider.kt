package dev.rbustillos.chucknorris.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceProvider {
    companion object {
        private fun clientProvider() : OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientProvider())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <API> createService(apiClass: Class<API>): API = retrofit.create(apiClass)
    }
}