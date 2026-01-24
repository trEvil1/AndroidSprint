package com.example.androidsprint.ui.recipes.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidsprint.databinding.RecipeListFragmentBinding
import com.example.androidsprint.ui.categories.CategoriesListFragmentArgs

class RecipeListFragment :
    Fragment() {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeListFragmentBinding must not be null"
        )
    val viewModel: RecipeListViewModel by viewModels()
    val args: CategoriesListFragmentArgs by navArgs()
    var categoryId: Int? = null

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
        viewModel.loadList(args.categoryId.id)
        initRecycler()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipesAdapter = RecipeListAdapter()
        viewModel.recipeListLiveData.observe(viewLifecycleOwner) { state ->
            recipesAdapter.dataset = state.recipesList ?: return@observe
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
        val action =
            RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment2(recipeId.toString())
        findNavController().navigate(action)
    }
}