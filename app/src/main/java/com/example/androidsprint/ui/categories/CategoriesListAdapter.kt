package com.example.androidsprint.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsprint.model.Category
import com.example.androidsprint.R
import com.example.androidsprint.databinding.ItemCategoryBinding
import java.io.InputStream

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
            .load(category.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .into(viewHolder.imageView)

        try {
            val inputStream: InputStream =
                viewHolder.itemView.context.assets.open(category.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("TAG", "Stack Trace", ex)
        }
        viewHolder.binding.cvCardCategory.setOnClickListener {
            itemClickListener?.onItemClick(
                category.id
            )
        }
    }

    override fun getItemCount() = dataSet.size
}