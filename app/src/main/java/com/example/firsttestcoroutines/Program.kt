package com.example.firsttestcoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
//    printHelloWorldByCoroutiens()
    incrementNumber()
}

private fun printHelloWorldByCoroutiens() {
    // print hello world using coroutines launch method
    // delay in this case not blocking the thread unlike the sleep method
    GlobalScope.launch {
        delay(1000)
        print("world  ")
    }
    print("Hello")
    Thread.sleep(1500)
}

private fun incrementNumber() {
    val result = AtomicInteger()
    for (i in 1..1_500_00)
        GlobalScope.launch {
            result.getAndIncrement()
        }
    Thread.sleep(1000)
    print(result.get())
}