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
        initRecycler()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipesAdapter = RecipeListAdapter(STUB.getRecipesByCategoryId(categoryId))
        binding.rvRecipes.adapter = recipesAdapter
        recipesAdapter.setOnItemClickListener(
            object :
                RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(
                        recipeId
                    )
                }
            }
        )
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = Bundle()
        bundle.putParcelable(ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}