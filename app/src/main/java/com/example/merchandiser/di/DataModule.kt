package com.example.merchandiser.di

import com.example.merchandiser.data.ApiFactory
import com.example.merchandiser.data.ApiService
import com.example.merchandiser.data.repositoriesImpls.AuthRepositoryImpl
import com.example.merchandiser.data.repositoriesImpls.ShopRepositoryImpl
import com.example.merchandiser.data.repositoriesImpls.TaskRepositoryImpl
import com.example.merchandiser.domain.repositories.AuthRepository
import com.example.merchandiser.domain.repositories.ShopRepository
import com.example.merchandiser.domain.repositories.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @ApplicationScope
    fun bindShopRepository(impl: ShopRepositoryImpl): ShopRepository

    @Binds
    @ApplicationScope
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

//    @Provides
//    fun provideApiService(): ApiService{
//        return ApiFactory.apiService
//    }
}