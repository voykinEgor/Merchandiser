package com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewPhotoHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val photoItem = itemView.findViewById<ImageView>(R.id.photoImageView)

}