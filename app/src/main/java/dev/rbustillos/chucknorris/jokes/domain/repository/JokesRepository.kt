package dev.rbustillos.chucknorris.jokes.domain.repository

import dev.rbustillos.chucknorris.jokes.domain.model.Category

interface JokesRepository {
    suspend fun doGetJokeCategories(): List<Category>
}