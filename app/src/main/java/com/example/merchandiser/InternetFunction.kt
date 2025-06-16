package com.example.merchandiser

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean{
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_WIFI)
}