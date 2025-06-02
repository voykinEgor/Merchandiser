package com.example.merchandiser.di

import com.example.merchandiser.presentation.auth.AuthFragment
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface Component {

    fun inject(fragment: AuthFragment)

}