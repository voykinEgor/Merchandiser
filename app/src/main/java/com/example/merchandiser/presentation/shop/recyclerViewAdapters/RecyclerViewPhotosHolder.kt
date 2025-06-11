package com.example.merchandiser.presentation.shop.recyclerViewAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewPhotosHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val shopName = itemView.findViewById<TextView>(R.id.categoryTextView)
    val status = itemView.findViewById<ImageView>(R.id.countPhoto)
}