package com.example.merchandiser.presentation.customTask.adapter.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.databinding.AddButtonBinding
import com.example.merchandiser.databinding.CategoryItemWithDeleteBinding
import com.example.merchandiser.domain.CategoryItem

sealed class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(categoryItem: CategoryItem?)

    class AddedCategoryViewHolder(private val binding: CategoryItemWithDeleteBinding, private val onRemoveClick: (CategoryItem) -> Unit) :
        CategoryViewHolder(binding.root) {

        override fun bind(categoryItem: CategoryItem?) {
            with(binding) {
                textViewShop.text = categoryItem!!.name
                deleteImageView.setOnClickListener { onRemoveClick(categoryItem) }
            }
        }
    }

    class AddButtonViewHolder(private val binding: AddButtonBinding, private val onAddClick: () -> Unit) :
        CategoryViewHolder(binding.root){

        override fun bind(categoryItem: CategoryItem?) {
                binding.root.setOnClickListener { onAddClick() }
            }
        }
}

class AddedCategoriesAdapter(
    private val _categories: List<CategoryItem>,
    private val onAddClick: () -> Unit,
    private val onRemoveClick: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>()  {

    companion object {
        private const val TYPE_CATEGORY = 0
        private const val TYPE_ADD_BUTTON = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return when (viewType) {
            TYPE_CATEGORY -> {
                val binding = CategoryItemWithDeleteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CategoryViewHolder.AddedCategoryViewHolder(binding, onRemoveClick)
            }
            else -> {
                val binding = AddButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CategoryViewHolder.AddButtonViewHolder(binding, onAddClick)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < _categories.size) TYPE_CATEGORY else TYPE_ADD_BUTTON
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder.AddedCategoryViewHolder -> holder.bind(_categories[position])
            is CategoryViewHolder.AddButtonViewHolder -> holder.bind(null)
            else -> {}
        }
    }

    override fun getItemCount() = _categories.size + 1
}