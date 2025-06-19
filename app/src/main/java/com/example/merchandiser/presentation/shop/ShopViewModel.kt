package com.example.merchandiser.presentation.shop

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.LOG
import com.example.merchandiser.domain.useCases.CompleteShopUseCase
import com.example.merchandiser.presentation.LoadingShopState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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



    fun completeShop(taskId: Int, shopId: Int, categories: List<Pair<Int, List<Uri>>>) {
        viewModelScope.launch {
            _loadingState.value = true

            val results = coroutineScope {
                categories.map { (categoryId, uris) ->
                    async {
                        val result = mutableListOf<LoadingShopState>()
                        completeShopUseCase
                            .completeShop(taskId, shopId, categoryId, uris)
                            .collect { state ->
                                result.add(state)
                            }
                        result
                    }
                }.awaitAll().flatten()
            }
            _loadingStateList.value = results
            _loadingState.value = false
        }
    }

}