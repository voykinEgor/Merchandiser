package com.example.merchandiser.presentation.task.recyclerViewAdapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer

class RecyclerViewCategoriesAdapter: ListAdapter<CategoryItemTransfer, RecyclerViewCategoriesHolder>(
    RecyclerViewCategoriesDiffItem()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewCategoriesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return RecyclerViewCategoriesHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewCategoriesHolder,
        position: Int
    ) {
        val category = getItem(position)
        holder.categoryName.text = category.name
    }
}