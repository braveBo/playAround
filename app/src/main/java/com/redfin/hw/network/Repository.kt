package com.redfin.hw.network

import com.redfin.hw.data.FoodTruck
import com.redfin.hw.data.ImageItem

class Repository {
    suspend fun getFoodTrucksFromRemote(): List<FoodTruck> {
        return FoodTruckApi.apiService.getFoodTrucks()
    }

    suspend fun getImages(): List<ImageItem> {
        return ImageApi.apiService.getImages()
    }
}