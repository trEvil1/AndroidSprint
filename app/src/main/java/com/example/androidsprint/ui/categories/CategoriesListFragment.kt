package com.example.androidsprint.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsprint.R
import com.example.androidsprint.databinding.FragmentListCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesListFragment : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FragmentListCategoriesBinding must not be null"
        )
    private val viewModel: CategoriesListViewModel by viewModels()

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
            if (state == null) {
                Toast.makeText(
                    context,
                    getString(R.string.exception),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                categoriesAdapter.dataSet = state.categoriesList
            }
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
        val action =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipeListFragment(
                category ?: throw IllegalArgumentException(
                    "Category with id $categoryId not found"
                )
            )
        findNavController().navigate(action)
    }
}
