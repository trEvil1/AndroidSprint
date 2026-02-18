package com.example.androidsprint.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Category
import kotlinx.coroutines.launch

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class CategoriesListState(
        val categoriesList: List<Category> = emptyList(),
    )

    private val recipesRepository = RecipeRepository(getApplication())
    private val _categoryLiveData = MutableLiveData<CategoriesListState>(CategoriesListState())
    val categoryLiveData: LiveData<CategoriesListState> = _categoryLiveData

    fun loadCategories() {
        viewModelScope.launch {
            val categoriesFromCache = recipesRepository.getCategoriesFromCache()
            val categoriesFromServer = recipesRepository.getCategory()
            if (categoriesFromServer != null) {
                recipesRepository.insertCategories(categoriesFromServer)
                _categoryLiveData.value =
                    _categoryLiveData.value?.copy(categoriesList = categoriesFromServer)
            } else _categoryLiveData.value =
                _categoryLiveData.value?.copy(categoriesList = categoriesFromCache ?: return@launch)
        }
    }
}