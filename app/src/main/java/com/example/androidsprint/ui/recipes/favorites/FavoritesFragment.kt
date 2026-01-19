package com.example.androidsprint.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.androidsprint.data.ARG_RECIPE
import com.example.androidsprint.R
import com.example.androidsprint.ui.recipes.recipe.RecipeFragment
import com.example.androidsprint.ui.recipes.recipe_list.RecipeListAdapter
import com.example.androidsprint.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment() {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FavoriteFragmentBinding must not be null"
        )
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadRecipes()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipeAdapter = RecipeListAdapter()
        viewModel.favoriteLiveData.observe(viewLifecycleOwner) { state ->
            val recipes = state.recipeList ?: return@observe
            recipeAdapter.dataset = recipes
            binding.rvFavorite.isVisible = recipes.isNotEmpty()
            binding.tvNoRecipes.isVisible = recipes.isEmpty()
        }
        binding.rvFavorite.adapter = recipeAdapter

        recipeAdapter.setOnItemClickListener(
            object :
                RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId:Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
        )
    }

    private fun openRecipeByRecipeId(recipeId:Int) {
        val bundle = bundleOf(ARG_RECIPE to recipeId)

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}