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
    fun runParallel() {
       runBlocking {
           val job1 = async { doJob1() }
           val job2 = async { doJob2() }

           // Wait for both jobs to complete
           job1.await()
           job2.await() }
    }

    private fun doJob2() {
        while (true) {
            println("doJob2 ==")
        }
    }

    private fun doJob1() {
        while (true) {
            println("doJob1 ==")
        }
    }
}