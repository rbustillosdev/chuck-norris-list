package dev.rbustillos.chucknorris.jokes.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rbustillos.chucknorris.core.common.ResultState
import dev.rbustillos.chucknorris.jokes.domain.model.Category
import dev.rbustillos.chucknorris.jokes.domain.useCase.DoGetCategoriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val doGetCategoriesUseCase: DoGetCategoriesUseCase) :
    ViewModel() {
    private val _categoriesResult = MutableLiveData<ResultState<List<Category>>?>(null)
    var categoriesResult: LiveData<ResultState<List<Category>>?> = _categoriesResult

    fun doGetCategories() {
        viewModelScope.launch {
            _categoriesResult.postValue(ResultState.Loading)
            try {
                _categoriesResult.postValue(ResultState.Success(doGetCategoriesUseCase()))
            } catch (e: Exception) {
                _categoriesResult.postValue(ResultState.Error(e))
            }
        }
    }


}