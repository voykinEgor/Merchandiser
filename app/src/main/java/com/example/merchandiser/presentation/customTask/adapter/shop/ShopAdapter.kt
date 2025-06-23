package com.example.merchandiser.presentation.customTask.adapter.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.databinding.ShopItemBinding
import com.example.merchandiser.domain.ShopItem

class StoresAdapter(
    private val stores: List<ShopItem>,
    private val onStoreClicked: (ShopItem) -> Unit
) : RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(private val binding: ShopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: ShopItem) {
            binding.textViewShop.text = shop.name
            binding.addressTextView.text = shop.address
            binding.doneImageView.visibility = View.INVISIBLE
            binding.root.setOnClickListener { onStoreClicked(shop) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ShopItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(stores[position])
    }

    override fun getItemCount() = stores.size
}