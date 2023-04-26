package com.redfin.hw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfin.hw.data.FoodTruck
import com.redfin.hw.network.Repository
import kotlinx.coroutines.launch

class FoodTruckViewModel(): ViewModel() {
    private val repository = Repository()
    private val result = MutableLiveData<Result>()
    private var cache: List<FoodTruck> = emptyList()

    fun getResult(): LiveData<Result> {
        return result
    }

    fun loadFoodTrucks() {
        viewModelScope.launch {
            try {
                val response = repository.getFoodTrucksFromRemote()
                result.postValue(Result.Success(response))
                cache = response
            } catch (e: Exception) {
                result.postValue(Result.Error(e.message.toString()))
            }
        }
    }

    fun getCachedFoodTrucks(): List<FoodTruck> {
        return cache
    }
}

sealed class Result {
    data class Success(val foodTruckList: List<FoodTruck>): Result()
    data class Error(val message: String?): Result()
    object Loading: Result()
}