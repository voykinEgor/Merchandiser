package com.example.merchandiser.presentation.customTask

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.LOG
import com.example.merchandiser.data.mappers.ShopMapper
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.useCases.CreateTaskUseCase
import com.example.merchandiser.domain.useCases.GetAllCategoriesUseCase
import com.example.merchandiser.domain.useCases.GetAllShopsUseCase
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.LongFunction
import javax.inject.Inject

class CustomTaskViewModel @Inject constructor(
    private val getAllShopUseCase: GetAllShopsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val shopMapper: ShopMapper
) : ViewModel() {

    private val DIVIDER = 60000

    private val _taskCreationResult = MutableLiveData<Boolean>()
    val taskCreationResult: LiveData<Boolean> = _taskCreationResult

    private val _shopsList: MutableLiveData<List<ShopItem>> = MutableLiveData()
    val shopsList: LiveData<List<ShopItem>> get() = _shopsList

    private val _categoriesList: MutableLiveData<List<CategoryItem>> = MutableLiveData()
    val categoriesList: LiveData<List<CategoryItem>> get() = _categoriesList

    fun getAllShop() {
        viewModelScope.launch {
            _shopsList.value = getAllShopUseCase.getAllShops()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            _categoriesList.value = getAllCategoriesUseCase.getAllCategories()
        }
    }

    fun createShopInTask(shopItem: ShopItem, categories: List<CategoryItem>): ShopsInTasks{
        val categoriesInTaskList = shopMapper.mapCategoriesListToCategoriesInTaskList(categories)
        return ShopsInTasks(shopItem, categoriesInTaskList, false)
    }

    fun createTaskItem(shopsInTasks: ShopsInTasks, categories: List<CategoryItem>, userId: Int): TaskItem{
        val id = (System.currentTimeMillis()/DIVIDER).toInt()
        val name = "Свободное задание"
        val date = formatDate(LocalDateTime.now())
        val taskItem = TaskItem(id, name, date, categories.toSet(), listOf(shopsInTasks), false)
        Log.d(LOG, "taskItem: $taskItem")
        viewModelScope.launch {
            _taskCreationResult.value = createTaskUseCase.createTask(taskItem, userId)
        }
        return taskItem
    }



    private fun formatDate(date: LocalDateTime): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        return date.format(formatter).toString()
    }
}