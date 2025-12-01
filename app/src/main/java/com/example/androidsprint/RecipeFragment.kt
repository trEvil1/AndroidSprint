package com.example.androidsprint

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsprint.databinding.FragmentListCategoriesBinding
import com.example.androidsprint.databinding.RecipeFragmentBinding
import com.example.androidsprint.databinding.RecipeListFragmentBinding

class RecipeFragment : Fragment(R.layout.recipe_fragment) {
    private var _binding: RecipeFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for RecipeFragmentBinding must not be null"
        )

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
        with(binding) {
            val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(ARG_RECIPE_ID, Recipe::class.java)
            } else {
                arguments?.getParcelable(ARG_RECIPE_ID)
            }
            tvRecipe.text = recipe?.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}