package com.example.androidsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.androidsprint.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment(R.layout.favorites_fragment) {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalAccessException(
            "Binding for FavoriteFragmentBinding must not be null"
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}