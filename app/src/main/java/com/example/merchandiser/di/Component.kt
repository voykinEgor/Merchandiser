package com.example.merchandiser.di

import android.app.Application
import com.example.merchandiser.presentation.auth.AuthFragment
import com.example.merchandiser.presentation.customTask.CustomTaskFragment
import com.example.merchandiser.presentation.mainMenu.MainMenuFragment
import com.example.merchandiser.presentation.map.MapFragment
import com.example.merchandiser.presentation.shop.ShopFragment
import com.example.merchandiser.presentation.task.TaskFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface Component {

    fun inject(fragment: AuthFragment)

    fun inject(fragment: MainMenuFragment)

    fun inject(fragment: TaskFragment)

    fun inject(fragment: ShopFragment)

    fun inject(fragment: MapFragment)

    fun inject(fragment: CustomTaskFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): com.example.merchandiser.di.Component
    }

}