package com.example.merchandiser.presentation.mainMenu.recyclerViewAdapters

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.TaskItem

class RecyclerViewDiffItem: DiffUtil.ItemCallback<TaskItem>() {
    override fun areItemsTheSame(
        oldItem: TaskItem,
        newItem: TaskItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TaskItem,
        newItem: TaskItem
    ): Boolean {
        return oldItem == newItem
    }
}