package com.redfin.hw.utils

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class IO {
    fun runBlockTest() =
        // 堵塞当前线程
        runBlocking {
        launch {
            println("Main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.IO) {
            println("IO            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    fun load() {
        MainScope().launch {
            while (true) {
                println("=== viewModelScope ${Thread.currentThread().name}")
                delay(100)
            }
        }
    }

    fun runBlockingLoad() {
        GlobalScope.launch {
            while (true) {
                delay(100)
                println("=== runBlocking ${Thread.currentThread().name}")
            }
        }
    }
}