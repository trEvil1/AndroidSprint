package com.example.androidsprint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.databinding.ItemIngredientBinding

class IngredientsAdapter(val dataset: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.item_ingredient, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val ingredient = dataset[position]
        holder.ingredients.text = ingredient.description
        holder.quantity.text = "${ingredient.quantity} ${ingredient.unitOfMeasure}"
    }

    override fun getItemCount() = dataset.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemIngredientBinding.bind(view)

        val ingredients = binding.tvIngredient
        val quantity = binding.tvQuantity
    }
}