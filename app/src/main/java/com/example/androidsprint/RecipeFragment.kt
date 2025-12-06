package com.example.androidsprint

import android.R
import android.app.FragmentManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint.databinding.RecipeFragmentBinding
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {
    private var _binding: RecipeFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeFragmentBinding must not be null"
        )
    private var recipe: Recipe? = null

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
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
        binding.tvRecipe.text = recipe?.title
        val recipeImage = view.context.assets.open(recipe?.imageUrl.toString())
        val drawable = Drawable.createFromStream(recipeImage, null)
        binding.ivRecipe.setImageDrawable(drawable)
        initRecyclerIngredients()
        initRecyclerMethod()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerIngredients() {
        val ingredientsAdapter = IngredientsAdapter(recipe!!.ingredients)
        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvIngredients.addItemDecoration(
            DividerItemDecoration(
                binding.rvIngredients.context,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    private fun initRecyclerMethod() {
        val methodAdapter = MethodAdapter(recipe!!.method)
        binding.rvMethod.adapter = methodAdapter
        binding.rvMethod.addItemDecoration(
            DividerItemDecoration(
                binding.rvMethod.context,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }
}