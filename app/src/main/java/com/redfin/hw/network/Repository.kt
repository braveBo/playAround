package com.redfin.hw.network

import com.redfin.hw.data.FoodTruck
import com.redfin.hw.data.ImageItem
import retrofit2.Response

class Repository {
    suspend fun getFoodTrucksFromRemote(): List<FoodTruck> {
        return FoodTruckApi.apiService.getFoodTrucks()
    }

    suspend fun getImages(): Response<List<ImageItem>> {
        return ImageApi.apiService.getImages()
    }
}