package com.example.androidsprint.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsprint.R
import com.example.androidsprint.data.URL_RECIPE
import com.example.androidsprint.databinding.ItemCategoryBinding
import com.example.androidsprint.model.Category

class CategoriesListAdapter() :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    var dataSet: List<Category> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCategoryBinding.bind(view)

        val imageView: ImageView = binding.ivBurgerCategory
        val titleTextView: TextView = binding.tvTitleCategories
        val descriptionTextView: TextView = binding.tvDescription
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(
            R.layout.item_category, viewGroup, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        viewHolder.descriptionTextView.text = category.description
        viewHolder.titleTextView.text = category.title

        Glide
            .with(viewHolder.itemView.context)
            .load( "${URL_RECIPE}images/${category.imageUrl}" )
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(viewHolder.imageView)

        viewHolder.binding.cvCardCategory.setOnClickListener {
            itemClickListener?.onItemClick(
                category.id
            )
        }
    }

    override fun getItemCount() = dataSet.size
}