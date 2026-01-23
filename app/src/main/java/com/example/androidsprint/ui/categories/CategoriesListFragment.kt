package com.example.androidsprint.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidsprint.R
import com.example.androidsprint.data.ARG_CATEGORY_ID
import com.example.androidsprint.data.ARG_CATEGORY_IMAGE_URL
import com.example.androidsprint.data.ARG_CATEGORY_NAME
import com.example.androidsprint.databinding.FragmentListCategoriesBinding
import com.example.androidsprint.model.Category
import com.example.androidsprint.ui.recipes.recipe_list.RecipeListFragmentDirections

class CategoriesListFragment : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FragmentListCategoriesBinding must not be null"
        )
    val viewModel: CategoriesListViewModel by viewModels()
    val args: CategoriesListFragmentArgs by navArgs<CategoriesListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategories()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter()
        viewModel.categoryLiveData.observe(viewLifecycleOwner) { state ->
            categoriesAdapter.dataSet = state.categoriesList ?: return@observe
        }

        binding.rvCategories.adapter = categoriesAdapter
        categoriesAdapter.setOnItemClickListener(
            object :
                CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(
                        categoryId
                    )
                }
            },
        )
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val category =
            viewModel.categoryLiveData.value?.categoriesList?.find { it.id == categoryId }
        val categoryName = category?.title
        val categoryImageUrl = category?.imageUrl
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl
        )
        val action =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipeListFragment(
                categoryId.toString()
            )
        findNavController().navigate(action)
    }
}