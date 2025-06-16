package com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.domain.Photo
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter.RecyclerViewPhotoAdapter
import java.net.URI
import androidx.core.net.toUri


class RecyclerViewItemCategoryAdapter: ListAdapter<CategoryItemTransfer, RecyclerViewItemCategoryHolder>(
    RecyclerViewItemCategoryDiffItem()
) {

    val listPhotos: ArrayList<Photo> = arrayListOf(Photo(0, "".toUri()), Photo(1, "".toUri()), Photo(2, "".toUri()), Photo(3, "".toUri()), Photo(4, "".toUri()))

    val photoAdapter = RecyclerViewPhotoAdapter()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewItemCategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attach_photos_item, parent, false)
        return RecyclerViewItemCategoryHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewItemCategoryHolder,
        position: Int
    ) {

        holder.photosRV.adapter = photoAdapter
        photoAdapter.submitList(listPhotos)
        val category = getItem(position)
        holder.shopName.text = formatCategory(category.name)
        holder.countPhoto.text = "0"
    }

    private fun formatCategory(category: String): String{
        return "$category: "
    }
}