package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer

class RecyclerViewShopsAdapter: ListAdapter<ShopItemTransfer, RecyclerViewShopsHolder>(
    RecyclerViewShopsDiffItem()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewShopsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item, parent, false)
        return RecyclerViewShopsHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewShopsHolder,
        position: Int
    ) {
        val shop = getItem(position)
        holder.shopName.text = shop.name

        holder.status.visibility = View.INVISIBLE
    }
}