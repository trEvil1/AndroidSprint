package com.example.androidsprint.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsprint.R
import com.example.androidsprint.databinding.FavoritesFragmentBinding
import com.example.androidsprint.ui.recipes.recipe_list.RecipeListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FavoriteFragmentBinding must not be null"
        )
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val appContainer = (requireActivity().application as RecipeApplication).appContainer
//        viewModel = appContainer.favoritesViewModelFactory.create()
    }

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
            if (state == null) {
                Toast.makeText(
                    context,
                    getString(R.string.exception),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val recipes = state.recipeList ?: return@observe
                recipeAdapter.dataset = recipes
                binding.rvFavorite.isVisible = recipes.isNotEmpty()
                binding.tvNoRecipes.isVisible = recipes.isEmpty()
            }
        }
        binding.rvFavorite.adapter = recipeAdapter

        recipeAdapter.setOnItemClickListener(
            object :
                RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
        )
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment2(recipeId.toString())
        findNavController().navigate(action)
    }
}