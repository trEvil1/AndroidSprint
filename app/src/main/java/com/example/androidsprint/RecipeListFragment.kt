package com.example.androidsprint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.androidsprint.databinding.RecipeListFragmentBinding


class RecipeListFragment(mainContainer: Int, bundle: Bundle) :
    Fragment(R.layout.recipe_list_fragment) {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeListFragmentBinding must not be null"
        )
    var categoryId: Int? = null
    var categoryImageUrl: String? = null
    var categoryName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
    }
}
