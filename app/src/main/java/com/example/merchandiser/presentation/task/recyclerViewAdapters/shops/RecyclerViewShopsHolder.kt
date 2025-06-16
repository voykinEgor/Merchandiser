package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewShopsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val shopName = itemView.findViewById<TextView>(R.id.textViewShop)
    val status = itemView.findViewById<ImageView>(R.id.doneImageView)
}