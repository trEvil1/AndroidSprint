package com.example.androidsprint

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint.databinding.RecipeFragmentBinding
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
        val divider = MaterialDividerItemDecoration(
            binding.rvIngredients.context,
            DividerItemDecoration.VERTICAL
        )
        divider.setDividerColorResource(binding.rvIngredients.context,R.color.line_color)
        divider.isLastItemDecorated = false
        binding.rvIngredients.addItemDecoration(divider)
    }

    private fun initRecyclerMethod() {
        val methodAdapter = MethodAdapter(recipe!!.method)
        binding.rvMethod.adapter = methodAdapter
        val divider = MaterialDividerItemDecoration(
            binding.rvMethod.context,
            DividerItemDecoration.VERTICAL
        )
        divider.setDividerColorResource(binding.rvMethod.context,R.color.line_color)
        divider.isLastItemDecorated = false
        binding.rvMethod.addItemDecoration(divider)
    }
}