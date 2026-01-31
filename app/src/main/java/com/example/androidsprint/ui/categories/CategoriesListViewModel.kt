package com.example.androidsprint.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Category

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class CategoriesListState(
        val categoriesList: List<Category>? = emptyList(),
    )
    private val recipesRepository = RecipeRepository()
    private val _categoryLiveData = MutableLiveData<CategoriesListState>()
    val categoryLiveData: LiveData<CategoriesListState> = _categoryLiveData

    fun loadCategories() {
        recipesRepository.threadPool.execute {
            _categoryLiveData.postValue(
                CategoriesListState(
                    categoriesList = recipesRepository.getCategory(),
                )
            )
        }

    }
}