package com.example.androidsprint.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.R
import com.example.androidsprint.databinding.ItemIngredientBinding
import com.example.androidsprint.model.Ingredient
import java.math.BigDecimal

class IngredientsAdapter() :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    var quantity: BigDecimal = BigDecimal(1)
    var dataset: List<Ingredient> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        val quantityAmount =
            if (ingredient.quantity.isDigitsOnly() || ingredient.quantity.toDoubleOrNull() != null) {
                BigDecimal(ingredient.quantity).multiply(quantity).stripTrailingZeros().let {
                    if (it.scale() <= 0) it.setScale(0) else it
                }.toString()
            } else ingredient.quantity
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
        quantity = BigDecimal(progress)
        notifyDataSetChanged()
    }
}