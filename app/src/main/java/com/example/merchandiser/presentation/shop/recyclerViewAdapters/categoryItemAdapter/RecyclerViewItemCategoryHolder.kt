package com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewItemCategoryHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val shopName = itemView.findViewById<TextView>(R.id.categoryTextView)
    val countPhoto = itemView.findViewById<TextView>(R.id.countPhoto)
    val photosRV = itemView.findViewById<RecyclerView>(R.id.recyclerViewPhotos)
}