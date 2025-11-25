package com.example.androidsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream

class CategoriesListAdapter(val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivBurgerCategory)
        val titleTextView: TextView? = view.findViewById(R.id.tvTitleCategories)
        val descriptionTextView: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        viewHolder.descriptionTextView.text = category.description
        viewHolder.titleTextView?.text = category.title
        try {
            val inputStream: InputStream? =
                viewHolder.itemView.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("TAG", "Stack Trace", ex)
        }

    }

    override fun getItemCount() = dataSet.size

}
