package com.example.merchandiser.di

import androidx.lifecycle.ViewModel
import com.example.merchandiser.presentation.auth.AuthViewModel
import com.example.merchandiser.presentation.mainMenu.MainMenuViewModel
import com.example.merchandiser.presentation.task.TaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindsAuthViewModel(impl: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    @Binds
    fun bindsMainMenuViewModel(impl: MainMenuViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    @Binds
    fun bindsTaskViewModel(impl: TaskViewModel): ViewModel

}