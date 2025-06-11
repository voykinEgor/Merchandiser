package com.example.merchandiser.presentation.shop.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer

class RecyclerViewPhotosAdapter: ListAdapter<CategoryItemTransfer, RecyclerViewPhotosHolder>(
    RecyclerViewPhotosDiffItem()
) {

    var onItemClickListener: ((CategoryItemTransfer) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewPhotosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attach_photos_item, parent, false)
        return RecyclerViewPhotosHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewPhotosHolder,
        position: Int
    ) {
        val category = getItem(position)
        holder.shopName.text = category.name
        holder.status.visibility = View.INVISIBLE

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(category)
        }
    }
}