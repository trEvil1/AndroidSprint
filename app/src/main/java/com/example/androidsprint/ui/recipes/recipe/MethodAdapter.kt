package com.example.androidsprint.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.R
import com.example.androidsprint.databinding.ItemMethodBinding

class MethodAdapter() :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {
    lateinit var dataset: List<String>

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMethodBinding.bind(view)
        val method = binding.tvMethod
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.item_method, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val method = dataset[position]
        holder.method.text = "${position + 1}. $method"
    }

    override fun getItemCount() = dataset.size
}