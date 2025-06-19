package com.example.merchandiser.presentation.customTask.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merchandiser.databinding.BottomSheetShopSelectionBinding
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.presentation.customTask.adapter.shop.StoresAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShopBottomSheet(
    private val shopList: List<ShopItem>
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetShopSelectionBinding? = null
    private val binding get() = _binding!!

    private var onStoreSelected: ((ShopItem) -> Unit)? = null

    private lateinit var adapter: StoresAdapter

    // Для передачи callback из Activity/Fragment
    fun setOnStoreSelectedListener(listener: (ShopItem) -> Unit) {
        onStoreSelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetShopSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StoresAdapter(shopList) { selectedStore ->
            onStoreSelected?.invoke(selectedStore)
            dismiss()
        }

        binding.rvStores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ShopBottomSheet.adapter // Устанавливаем адаптер
            setHasFixedSize(true) // Оптимизация, если все элементы одинакового размера
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Важно: обнуляем binding при уничтожении View
    }
}