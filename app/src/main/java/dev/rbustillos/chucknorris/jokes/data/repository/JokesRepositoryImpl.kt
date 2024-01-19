package dev.rbustillos.chucknorris.jokes.data.repository

import dev.rbustillos.chucknorris.jokes.data.remote.RemoteJokesDataSource
import dev.rbustillos.chucknorris.jokes.domain.model.Category
import dev.rbustillos.chucknorris.jokes.domain.repository.JokesRepository
import javax.inject.Inject

class JokesRepositoryImpl @Inject constructor(private val dataSource: RemoteJokesDataSource) :
    JokesRepository {
    override suspend fun doGetJokeCategories(): List<Category> {
        val categoriesFromRemote = dataSource.doGetCategories()

        return categoriesFromRemote!!.map { Category(name = it) }.toList()
    }
}