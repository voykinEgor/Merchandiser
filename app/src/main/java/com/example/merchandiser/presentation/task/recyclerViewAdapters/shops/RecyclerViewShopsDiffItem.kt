package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer

class RecyclerViewShopsDiffItem: DiffUtil.ItemCallback<ShopItemTransfer>() {
    override fun areItemsTheSame(
        oldItem: ShopItemTransfer,
        newItem: ShopItemTransfer
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ShopItemTransfer,
        newItem: ShopItemTransfer
    ): Boolean {
        return oldItem == newItem
    }

}