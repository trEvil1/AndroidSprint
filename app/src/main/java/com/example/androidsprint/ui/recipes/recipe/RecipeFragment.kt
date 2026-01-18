package com.example.androidsprint.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint.data.ARG_RECIPE
import com.example.androidsprint.R
import com.example.androidsprint.databinding.RecipeFragmentBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import androidx.fragment.app.viewModels

class RecipeFragment : Fragment() {
    private var _binding: RecipeFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeFragmentBinding must not be null"
        )
    private val viewModel: RecipeViewModel by viewModels()
    private var ingredientsAdapter: IngredientsAdapter? = null

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
        val recipeId = arguments?.getInt(ARG_RECIPE)

        viewModel.loadRecipe(recipeId ?: return)
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.ibFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        viewModel.recipeLiveData.observe(viewLifecycleOwner) { state ->
            binding.tvRecipe.text = state.recipe?.title
            binding.ibFavorite.setImageResource(
                if (state.isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
            )

            binding.ivRecipe.setImageDrawable(state.recipeImage)
            binding.tvPortionsCount.text = state.portionCount.toString()
        }

        binding.sbPortions.setOnSeekBarChangeListener(
            PortionSeekBarListener {
                viewModel.onPortionsCountChanged()
            })

        val methodAdapter = MethodAdapter()
        ingredientsAdapter = IngredientsAdapter()
        viewModel.recipeLiveData.observe(viewLifecycleOwner) { state ->
            methodAdapter.dataset = state.recipe?.method ?: return@observe
            binding.rvMethod.adapter = methodAdapter
            val dividerMethod = MaterialDividerItemDecoration(
                binding.rvMethod.context,
                DividerItemDecoration.VERTICAL
            )
                .apply {
                    dividerColor =
                        ContextCompat.getColor(binding.rvMethod.context, R.color.line_color)
                    dividerInsetStart = resources.getDimensionPixelSize(R.dimen.margin_small)
                    dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.margin_small)
                    isLastItemDecorated = false
                }
            binding.rvMethod.addItemDecoration(dividerMethod)


            ingredientsAdapter?.dataset = state.recipe.ingredients
            binding.rvIngredients.adapter = ingredientsAdapter
            val dividerIngredient = MaterialDividerItemDecoration(
                binding.rvIngredients.context,
                DividerItemDecoration.VERTICAL
            ).apply {
                dividerColor =
                    ContextCompat.getColor(binding.rvIngredients.context, R.color.line_color)
                dividerInsetStart = resources.getDimensionPixelSize(R.dimen.margin_small)
                dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.margin_small)
                isLastItemDecorated = false
            }
            binding.rvIngredients.addItemDecoration(dividerIngredient)
        }
    }

    class PortionSeekBarListener(val onChangeIngredients: (Int) -> Unit) :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
}