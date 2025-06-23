package com.example.merchandiser.presentation.task.recyclerViewAdapters.shops

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.LOG
import com.example.merchandiser.R
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks

class RecyclerViewShopsAdapter: ListAdapter<ShopsInTasks, RecyclerViewShopsHolder>(
    RecyclerViewShopsDiffItem()
) {

    var onItemClickListener: ((ShopsInTasks) -> Unit)? = null

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
        holder.shopName.text = shop.shopItem.name
        holder.shopAdress.text = shop.shopItem.address

        Log.d(LOG, "shop status: ${shop.status}")

        if (shop.status){
            holder.status.visibility = View.VISIBLE
        }else{
            holder.status.visibility = View.INVISIBLE
        }


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(shop)
        }
    }
}