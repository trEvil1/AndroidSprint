package com.example.androidsprint.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Category
import kotlinx.coroutines.launch

class CategoriesListViewModel(private val repository: RecipeRepository) : ViewModel() {
    data class CategoriesListState(
        val categoriesList: List<Category> = emptyList(),
    )

    private val _categoryLiveData = MutableLiveData<CategoriesListState>(CategoriesListState())
    val categoryLiveData: LiveData<CategoriesListState> = _categoryLiveData

    fun loadCategories() {
        viewModelScope.launch {
            val categoriesFromCache = repository.getCategoriesFromCache()
            if (!categoriesFromCache.isNullOrEmpty())
                _categoryLiveData.value = CategoriesListState(categoriesFromCache)

            val categoriesFromServer = repository.getCategory()
            if (!categoriesFromServer.isNullOrEmpty()) {
                repository.insertCategories(categoriesFromServer)
                _categoryLiveData.value = CategoriesListState(categoriesFromServer)
            }

        }
    }
}