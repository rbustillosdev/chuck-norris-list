package dev.rbustillos.chucknorris.jokes.data.remote

import dev.rbustillos.chucknorris.core.network.JokesAPIService
import javax.inject.Inject

class RemoteJokesDataSource @Inject constructor(private val service: JokesAPIService) {
    fun doGetCategories(): List<String>? {
        val response = service.getCategories().execute()
        if (response.code() in 200..299) {
            return response.body()!!
        }
        return null
    }
}