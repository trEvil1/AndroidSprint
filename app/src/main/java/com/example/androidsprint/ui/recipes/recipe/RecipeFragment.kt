package com.example.androidsprint.ui.recipes.recipe

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint.data.ARG_RECIPE
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.R
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.databinding.RecipeFragmentBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class RecipeFragment : Fragment() {
    private var _binding: RecipeFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeFragmentBinding must not be null"
        )
    private var recipe: Recipe? = null
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.insideSelectedItem.observe(viewLifecycleOwner, Observer {
            Log.v("!!!", "??????????")
        })

        recipe =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
            } else {
                arguments?.getParcelable(ARG_RECIPE)
            }

        binding.tvRecipe.text = recipe?.title
        val recipeImage = view.context.assets.open(recipe?.imageUrl.toString())
        val drawable = Drawable.createFromStream(recipeImage, null)
        binding.ivRecipe.setImageDrawable(drawable)
        initUI()
        initRecyclerIngredients()
        initRecyclerMethod()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerIngredients() {
        val ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }
        binding.rvIngredients.adapter = ingredientsAdapter
        val divider = MaterialDividerItemDecoration(
            binding.rvIngredients.context,
            DividerItemDecoration.VERTICAL
        ).apply {
            dividerColor = ContextCompat.getColor(binding.rvIngredients.context, R.color.line_color)
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.margin_small)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.margin_small)
            isLastItemDecorated = false
        }
        binding.rvIngredients.addItemDecoration(divider)
        binding.sbPortions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                ingredientsAdapter?.updateIngredients(progress)
                binding.tvPortionsCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initRecyclerMethod() {
        val methodAdapter = recipe?.method?.let { MethodAdapter(it) }
        binding.rvMethod.adapter = methodAdapter
        val divider = MaterialDividerItemDecoration(
            binding.rvMethod.context,
            DividerItemDecoration.VERTICAL
        )
            .apply {
                dividerColor = ContextCompat.getColor(binding.rvMethod.context, R.color.line_color)
                dividerInsetStart = resources.getDimensionPixelSize(R.dimen.margin_small)
                dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.margin_small)
                isLastItemDecorated = false
            }
        binding.rvMethod.addItemDecoration(divider)
    }

    private fun initUI() {
        val recipeId = recipe?.id.toString()
        val favoriteSet = getFavorites()

        if (recipeId in favoriteSet) {
            binding.ibFavorite.setImageResource(R.drawable.ic_heart)
        } else binding.ibFavorite.setImageResource(R.drawable.ic_heart_empty)

        binding.ibFavorite.setOnClickListener {
            if (recipeId in favoriteSet) {
                favoriteSet.remove(recipeId)
                binding.ibFavorite.setImageResource(R.drawable.ic_heart_empty)
                saveFavorite(favoriteSet)
            } else {
                favoriteSet.add(recipeId)
                binding.ibFavorite.setImageResource(R.drawable.ic_heart)
                saveFavorite(favoriteSet)
            }
        }
    }

    private fun saveFavorite(set: Set<String>) {
        val sharedPreferences: SharedPreferences = activity?.getSharedPreferences(
            KEY_PREFERENCE_FILE,
            Context.MODE_PRIVATE
        ) ?: return
        sharedPreferences.edit {
            putStringSet(KEY_FAVORITE_PREFS, set)
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