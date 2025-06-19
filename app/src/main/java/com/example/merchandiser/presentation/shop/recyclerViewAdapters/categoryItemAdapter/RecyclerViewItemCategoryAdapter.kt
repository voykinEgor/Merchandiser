package com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.Photo
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter.RecyclerViewPhotoAdapter


class RecyclerViewItemCategoryAdapter: ListAdapter<CategoryInTasks, RecyclerViewItemCategoryHolder>(
    RecyclerViewItemCategoryDiffItem()
) {

    var listPhotos: ArrayList<Photo> = arrayListOf(Photo(0, "".toUri()))

    var onPhotoClick: ((Photo, CategoryInTasks) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewItemCategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attach_photos_item, parent, false)
        return RecyclerViewItemCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewItemCategoryHolder, position: Int) {
        val category = getItem(position)

        val photoAdapter = RecyclerViewPhotoAdapter()
        photoAdapter.setCurrentCategory(category)

        photoAdapter.onItemClickListener = { photo, cat ->
            onPhotoClick?.invoke(photo, cat)
        }

        holder.photosRV.adapter = photoAdapter

        holder.shopName.text = formatCategory(category.category.name)
        holder.countPhoto.text = "${category.uriList?.size ?: 0}"
    }


    private fun formatCategory(category: String): String{
        return "$category: "
    }
}