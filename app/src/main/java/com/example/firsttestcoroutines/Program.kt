package com.example.firsttestcoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun main(args: Array<String>) {

    // print hello world using coroutines launch method
    // delay in this case not blocking the thread unlike the sleep method
    GlobalScope.launch {
        delay(1000)
        print("world  ")
    }
    print("Hello")
    Thread.sleep(1500)

}