package com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.domain.Photo

class RecyclerViewPhotoDiffItem: DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean {
        return oldItem == newItem
    }

}