package dev.rbustillos.chucknorris.jokes.domain.useCase

import dev.rbustillos.chucknorris.jokes.domain.model.Category
import dev.rbustillos.chucknorris.jokes.domain.repository.JokesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import javax.inject.Inject

class DoGetCategoriesUseCase @Inject constructor(private val jokesRepository: JokesRepository) {
    suspend operator fun invoke() : List<Category> = (Dispatchers.IO) { jokesRepository.doGetJokeCategories() }
}