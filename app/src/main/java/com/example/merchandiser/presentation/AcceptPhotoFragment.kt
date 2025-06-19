package com.example.merchandiser.presentation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.databinding.FragmentAcceptPhotoBinding
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.shop.ShopFragmentDirections


class AcceptPhotoFragment : Fragment() {

    private var _binding: FragmentAcceptPhotoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AcceptPhotoFragmentArgs>()

    private lateinit var categoryInTaskItem: CategoryInTasks
    private lateinit var shopInTaskItem: ShopsInTasks

    private val taskItem: TaskItem by lazy {
        args.taskItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcceptPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoUri = args.uri.toUri()
        categoryInTaskItem = args.categoryInShop
        shopInTaskItem = args.shopsInTasks
        binding.photoImageView.setImageURI(photoUri)

        binding.acceptButton.setOnClickListener {
            insertPhotoToList(photoUri, categoryInTaskItem)
            val updatedShopItem = updateShopsInTasks(shopInTaskItem, categoryInTaskItem)
            findNavController().navigate(AcceptPhotoFragmentDirections.actionAcceptPhotoFragmentToShopFragment(updatedShopItem, taskItem))
        }

        binding.declineButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun insertPhotoToList(photoUri: Uri, categoryInTaskItem: CategoryInTasks) {
        categoryInTaskItem.uriList?.add(photoUri) ?: run {
            categoryInTaskItem.uriList = mutableListOf(photoUri)
        }
    }

    fun updateShopsInTasks(shopInTaskItem: ShopsInTasks, updatedCategory: CategoryInTasks): ShopsInTasks{
        val updatedCategories = shopInTaskItem.categories.map {categoryInTask ->
            if(categoryInTask.category.id == updatedCategory.category.id) updatedCategory else categoryInTask
        }
        return shopInTaskItem.copy(categories = updatedCategories)
    }
}