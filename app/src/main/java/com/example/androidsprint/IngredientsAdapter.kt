package com.example.androidsprint

import android.icu.math.BigDecimal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.databinding.ItemIngredientBinding

class IngredientsAdapter(val dataset: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    var quantity = 1

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
        val quantityAmount = BigDecimal(ingredient.quantity.toDouble() * quantity)
        holder.quantity.text = "$quantityAmount ${ingredient.unitOfMeasure}"
        holder.ingredients.text = ingredient.description
    }

    override fun getItemCount() = dataset.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemIngredientBinding.bind(view)

        val ingredients = binding.tvIngredient
        val quantity = binding.tvQuantity
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }
}