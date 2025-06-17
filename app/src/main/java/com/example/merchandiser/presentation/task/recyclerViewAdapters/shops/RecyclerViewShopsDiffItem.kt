package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks

class RecyclerViewShopsDiffItem: DiffUtil.ItemCallback<ShopsInTasks>() {
    override fun areItemsTheSame(
        oldItem: ShopsInTasks,
        newItem: ShopsInTasks
    ): Boolean {
        return oldItem.shopItem.id == newItem.shopItem.id
    }

    override fun areContentsTheSame(
        oldItem: ShopsInTasks,
        newItem: ShopsInTasks
    ): Boolean {
        return oldItem == newItem
    }

}