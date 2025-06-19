package com.example.merchandiser.presentation.customTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.useCases.GetAllCategoriesUseCase
import com.example.merchandiser.domain.useCases.GetAllShopsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomTaskViewModel @Inject constructor(
    private val getAllShopUseCase: GetAllShopsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

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
}