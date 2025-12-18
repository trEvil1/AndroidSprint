package com.example.androidsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsprint.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment() {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FavoriteFragmentBinding must not be null"
        )
    private val favSet = RecipeFragment().getFavorites()

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
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipeAdapter = RecipeListAdapter(STUB.getRecipesByIds(favSet))
        binding.rvFavorite.adapter = recipeAdapter
        recipeAdapter.setOnItemClickListener(
            object :
                RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    RecipeListFragment().openRecipeByRecipeId(recipeId)
                }
            }
        )
    }
}