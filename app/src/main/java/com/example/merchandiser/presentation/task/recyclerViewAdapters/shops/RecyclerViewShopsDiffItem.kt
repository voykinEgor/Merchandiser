package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.ShopItem

class RecyclerViewShopsDiffItem: DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(
        oldItem: ShopItem,
        newItem: ShopItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ShopItem,
        newItem: ShopItem
    ): Boolean {
        return oldItem == newItem
    }

}