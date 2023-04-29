package com.redfin.hw.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfin.hw.data.ImageItem
import com.redfin.hw.network.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class ImageViewModel: ViewModel() {
    private val repository = Repository()
    private val _images = MutableSharedFlow<List<ImageItem>>()
    val images: SharedFlow<List<ImageItem>> = _images
    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error
    fun load() {
        viewModelScope.launch {
            try{
                val response = repository.getImages()
                if (response.isSuccessful) {
                    _images.emit(response.body()!!)
                    println("bbbbbbbbbb")
                } else {
                    _error.emit(HttpException(response))
                    println("cccccccccc")
                }
            } catch (e: Exception) {
                println("aaaaaaa ${e.message}")
                _error.emit(e)
            }
        }
    }
}