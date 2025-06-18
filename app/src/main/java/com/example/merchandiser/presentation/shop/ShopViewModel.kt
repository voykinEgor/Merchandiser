package com.example.merchandiser.presentation.shop

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.domain.useCases.CompleteShopUseCase
import com.example.merchandiser.presentation.LoadingShopState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val completeShopUseCase: CompleteShopUseCase
): ViewModel() {

    private val _loadingStateList = MutableLiveData<List<LoadingShopState>>()
    val loadingStateList: LiveData<List<LoadingShopState>> = _loadingStateList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState



    fun completeShop(taskId: Int, shopId: Int, categories: List<Pair<Int, List<Uri>>>){
        _loadingStateList.value = emptyList()
        viewModelScope.launch {
            _loadingState.value = true

            val results = mutableListOf<LoadingShopState>()

            for ((categoryId, uris) in categories){
                completeShopUseCase.completeShop(taskId, shopId, categoryId, uris).collect {state ->
                    results.add(state)
                }
            }

            _loadingStateList.value = results
            _loadingState.value = false
        }

    }
}