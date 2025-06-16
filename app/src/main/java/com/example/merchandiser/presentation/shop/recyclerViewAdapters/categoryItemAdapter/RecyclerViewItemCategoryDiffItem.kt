package com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.CategoryItem

class RecyclerViewItemCategoryDiffItem: DiffUtil.ItemCallback<CategoryItem>() {

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