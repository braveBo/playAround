package com.redfin.hw.network

import com.redfin.hw.data.FoodTruck

class Repository {
    suspend fun getFoodTrucksFromRemote(): List<FoodTruck> {
        return FoodTruckApi.apiService.getFoodTrucks()
    }
}