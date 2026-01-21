package com.example.androidsprint.ui.recipes.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsprint.R
import com.example.androidsprint.data.ARG_CATEGORY_ID
import com.example.androidsprint.data.ARG_CATEGORY_IMAGE_URL
import com.example.androidsprint.data.ARG_CATEGORY_NAME
import com.example.androidsprint.data.ARG_RECIPE
import com.example.androidsprint.databinding.RecipeListFragmentBinding

class RecipeListFragment :
    Fragment() {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeListFragmentBinding must not be null"
        )
    val viewModel: RecipeListViewModel by viewModels()
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
        viewModel.loadList(categoryId?:return)
        initRecycler()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipesAdapter = RecipeListAdapter()
        viewModel.recipeListLiveData.observe(viewLifecycleOwner){state ->
            recipesAdapter.dataset = state.recipesList?:return@observe
        }

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
        val bundle = bundleOf(ARG_RECIPE to recipeId)
        findNavController().navigate(R.id.recipeFragment,bundle)
    }
}