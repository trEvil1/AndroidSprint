package com.example.androidsprint.ui.recipes.recipe_list

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.R
import com.example.androidsprint.databinding.ItemRecipesBinding
import com.example.androidsprint.model.Recipe
import java.io.InputStream

class RecipeListAdapter() :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    var dataset: List<Recipe> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRecipesBinding.bind(view)

        val imageView: ImageView = binding.ivBurgerRecipe
        val titleTextView: TextView = binding.tvTitleBurgers
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.item_recipes, parent, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataset[position]
        holder.titleTextView.text = recipe.title
        try {
            val inputStream: InputStream =
                holder.itemView.context.assets.open(recipe.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("TAG", "Stack Trace", ex)
        }
        holder.binding.cvCardCategory.setOnClickListener {
            itemClickListener?.onItemClick(
                recipe.id
            )
        }
    }

    override fun getItemCount() = dataset.size
}