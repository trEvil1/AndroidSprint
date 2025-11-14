package com.example.androidsprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.androidsprint.databinding.FragmentListCategoriesBinding

class CategoriesListFragment: Fragment(R.layout.fragment_list_categories){
    private lateinit var binding: FragmentListCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentListCategoriesBinding.inflate(layoutInflater)
    }
}