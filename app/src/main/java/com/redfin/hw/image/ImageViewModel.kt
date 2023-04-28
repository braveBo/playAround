package com.redfin.hw.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfin.hw.data.ImageItem
import com.redfin.hw.network.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ImageViewModel: ViewModel() {
    private val repository = Repository()
    private val _images = MutableSharedFlow<List<ImageItem>>()
    val images: SharedFlow<List<ImageItem>> = _images
    fun load() {
        viewModelScope.launch {
            try{
                _images.emit(repository.getImages())
            } catch (e: Exception) {
                println("aaaaaaa ${e.message}")
            }
        }
    }
}