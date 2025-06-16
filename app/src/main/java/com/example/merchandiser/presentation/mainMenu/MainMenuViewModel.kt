package com.example.merchandiser.presentation.mainMenu

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.data.mappers.TaskMapper
import com.example.merchandiser.data.models.transfer.TaskItemTransfer
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.useCases.GetTaskListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val taskMapper: TaskMapper
): ViewModel() {

    private val _tasksList: MutableLiveData<List<TaskItem>> = MutableLiveData()
    val tasksList: LiveData<List<TaskItem>> get() = _tasksList

    fun getTasksList(userId: Int){
        viewModelScope.launch {
            val tasksList = getTaskListUseCase.getTaskList(userId)
            _tasksList.value = tasksList
        }

    }

    fun mapTaskDomainToTaskTransfer(taskItem: TaskItem): TaskItemTransfer{
        return taskMapper.mapTaskDomainToTaskTransfer(taskItem)
    }

}