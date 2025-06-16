package com.example.merchandiser.presentation.task.recyclerViewAdapters.categories

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer

class RecyclerViewCategoriesDiffItem: DiffUtil.ItemCallback<CategoryItemTransfer>() {
    override fun areItemsTheSame(
        oldItem: CategoryItemTransfer,
        newItem: CategoryItemTransfer
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryItemTransfer,
        newItem: CategoryItemTransfer
    ): Boolean {
        return oldItem == newItem
    }
}