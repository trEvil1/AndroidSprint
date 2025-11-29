package com.example.androidsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsprint.databinding.RecipeListFragmentBinding

class RecipeListFragment :
    Fragment(R.layout.recipe_list_fragment) {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeListFragmentBinding must not be null"
        )
    var categoryId: Int? = null
    var categoryImageUrl: String? = null
    var categoryName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeListFragment>(R.id.mainContainer)
        }
    }
}
