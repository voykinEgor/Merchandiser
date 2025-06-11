package com.example.merchandiser.presentation.task

import androidx.lifecycle.ViewModel
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer
import com.example.merchandiser.data.models.transfer.TaskItemTransfer
import javax.inject.Inject

class TaskViewModel @Inject constructor(): ViewModel() {

    fun getCategories(taskItem: TaskItemTransfer): Set<CategoryItemTransfer>{
        return taskItem.setCategoriesItems
    }

    fun getShops(taskItem: TaskItemTransfer): List<ShopItemTransfer>{
        return taskItem.listShops
    }
}