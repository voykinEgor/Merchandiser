package com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.CategoryItem

class RecyclerViewItemCategoryDiffItem: DiffUtil.ItemCallback<CategoryInTasks>() {

    override fun areItemsTheSame(
        oldItem: CategoryInTasks,
        newItem: CategoryInTasks
    ): Boolean {
        return oldItem.category.id == newItem.category.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryInTasks,
        newItem: CategoryInTasks
    ): Boolean {
        return oldItem == newItem
    }

}