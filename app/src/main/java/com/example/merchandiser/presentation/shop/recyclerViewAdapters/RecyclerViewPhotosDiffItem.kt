package com.example.merchandiser.presentation.shop.recyclerViewAdapters

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer

class RecyclerViewPhotosDiffItem: DiffUtil.ItemCallback<CategoryItemTransfer>() {

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