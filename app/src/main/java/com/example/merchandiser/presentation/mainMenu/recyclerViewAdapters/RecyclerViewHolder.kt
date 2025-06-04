package com.example.merchandiser.presentation.mainMenu.recyclerViewAdapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.R

class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textViewName = itemView.findViewById<TextView>(R.id.textViewTaskNumber)
    val textViewDateTo = itemView.findViewById<TextView>(R.id.textViewDateTo)
}