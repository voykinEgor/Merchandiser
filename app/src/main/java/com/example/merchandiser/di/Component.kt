package com.example.merchandiser.di

import com.example.merchandiser.presentation.auth.AuthFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface Component {

    fun inject(fragment: AuthFragment)

}