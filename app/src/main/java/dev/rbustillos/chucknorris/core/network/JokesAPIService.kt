package dev.rbustillos.chucknorris.core.network

import retrofit2.Call
import retrofit2.http.GET

interface JokesAPIService {
    @GET(jokesListByCategoryUrl)
    fun getCategories(): Call<List<String>>
}