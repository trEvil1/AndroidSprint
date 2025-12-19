package com.example.androidsprint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsprint.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment() {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FavoriteFragmentBinding must not be null"
        )


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
        val recipeAdapter = RecipeListAdapter(
            STUB.getRecipesByIds(
                getFavorites()
            )
        )
        binding.rvFavorite.adapter = recipeAdapter

        val recipes = STUB.getRecipesByIds(getFavorites())
        binding.rvFavorite.isVisible = recipes.isNotEmpty()
        binding.tvNoRecipes.isVisible = recipes.isEmpty()

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
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = Bundle()
        bundle.putParcelable(ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sp = activity?.getSharedPreferences(
            KEY_PREFERENCE_FILE, Context.MODE_PRIVATE
        )
        return HashSet(
            sp?.getStringSet(
                KEY_FAVORITE_PREFS, HashSet<String>()
            ) ?: mutableSetOf()
        )
    }
}