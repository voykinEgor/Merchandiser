package com.example.merchandiser.presentation.task.recyclerViewAdapters.categories

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.CategoryItem

class RecyclerViewCategoriesDiffItem: DiffUtil.ItemCallback<CategoryItem>() {
    override fun areItemsTheSame(
        oldItem: CategoryItem,
        newItem: CategoryItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryItem,
        newItem: CategoryItem
    ): Boolean {
        return oldItem == newItem
    }
}