package com.example.merchandiser.presentation.task.recyclerViewAdapters.categories

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewCategoriesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val categoryName = itemView.findViewById<TextView>(R.id.textViewShop)
}