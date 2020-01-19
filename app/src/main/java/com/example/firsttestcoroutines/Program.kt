package com.example.firsttestcoroutines

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
//    printHelloWorldByCoroutiens()
//    incrementNumber()
//    printHelloWorldByCoroutiens2()
//    printHelloWorldByCoroutiens3()
    printDote2()
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


// runBlocking block the thread if delay method is call
// launch didn't block the thread if delay method is call

// run coroutines inside coroutines

private fun printHelloWorldByCoroutiens2() = runBlocking {

    GlobalScope.launch {
        delay(1000)
        print("world  ")
    }
    print("Hello , ")

    doWork()
}

private fun printHelloWorldByCoroutiens3() = runBlocking {

  val job= launch {
        delay(1000)
        print("world  ")
    }
    print("Hello , ")

    job.join()
}
private fun printDote() = runBlocking {

    val job= launch {
        repeat(1000){
            delay(100)
            print(".")
        }
    }

    delay(2500)
/*    job.cancel()
    job.join()*/
    job.cancelAndJoin()
    print("done")
}
private fun printDote2() = runBlocking {

    val job= launch {
        repeat(1000){
            if (!isActive) return@launch
            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    print("done")
}

// suspend mean that this block will run in coroutiens block
private suspend fun doWork() {
    delay(1500)
}