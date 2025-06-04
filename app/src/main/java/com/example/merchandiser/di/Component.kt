package com.example.merchandiser.di

import com.example.merchandiser.presentation.auth.AuthFragment
import com.example.merchandiser.presentation.mainMenu.MainMenuFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface Component {

    fun inject(fragment: AuthFragment)

    fun inject(fragment: MainMenuFragment)
}