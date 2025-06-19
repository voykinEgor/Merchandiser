package com.example.merchandiser.presentation.customTask.adapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.databinding.CategoryItemBinding
import com.example.merchandiser.domain.CategoryItem

class CategoriesAdapter(
    private val _categories: List<CategoryItem>,
    private val onStoreClicked: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryItem) {
            binding.textViewShop.text = category.name
            binding.root.setOnClickListener { onStoreClicked(category) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(_categories[position])
    }

    override fun getItemCount() = _categories.size
}