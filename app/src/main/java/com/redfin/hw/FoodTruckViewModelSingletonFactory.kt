package com.redfin.hw

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

object FoodTruckViewModelSingletonFactory {
    private val viewModelStore = ViewModelStore()
    private val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
    val viewModel: FoodTruckViewModel by lazy {
        ViewModelProvider(viewModelStore, factory)[FoodTruckViewModel::class.java]
    }
}
