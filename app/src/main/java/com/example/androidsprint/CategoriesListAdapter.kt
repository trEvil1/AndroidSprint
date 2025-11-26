package com.example.androidsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint.databinding.ItemCategoryBinding
import java.io.InputStream

class CategoriesListAdapter(val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    val itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        val itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCategoryBinding.bind(view)

        val imageView: ImageView = binding.ivBurgerCategory
        val titleTextView: TextView = binding.tvTitleCategories
        val descriptionTextView: TextView = binding.tvDescription
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        viewHolder.descriptionTextView.text = category.description
        viewHolder.titleTextView.text = category.title
        try {
            val inputStream: InputStream =
                viewHolder.itemView.context.assets.open(category.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("TAG", "Stack Trace", ex)
        }
        viewHolder.binding.cvCardCategory.setOnClickListener { itemClickListener?.onItemClick() }
    }

    override fun getItemCount() = dataSet.size
}