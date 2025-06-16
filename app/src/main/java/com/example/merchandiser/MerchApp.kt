package com.example.merchandiser

import android.app.Application
import com.example.merchandiser.di.DaggerComponent

class MerchApp: Application() {

    val component by lazy {
        DaggerComponent.factory().create(this)
    }

}