package com.example.firsttestcoroutines

import kotlinx.coroutines.*
import java.lang.Thread.yield
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) = runBlocking {
    //    printHelloWorldByCoroutiens()
//    incrementNumber()
//    printHelloWorldByCoroutiens2()
//    printHelloWorldByCoroutiens3()
//    printDote2()
//    timeOut()
    val jobs = arrayListOf<Job>()
    createJobs(jobs)
    jobs.forEach { it -> it.join() }
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

    val job = launch {
        delay(1000)
        print("world  ")
    }
    print("Hello , ")

    job.join()
}

private fun printDote() = runBlocking {

    val job = launch {
        repeat(1000) {
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

    val job = launch {
        repeat(1000) {
            if (!isActive) return@launch
            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    print("done")
}

private fun timeOut() = runBlocking {

    // after time out it will be end the job
    val job = withTimeoutOrNull(100) {
        repeat(1000) {
            yield()
            print(".")
            Thread.sleep(1)
        }
    }
    if (job == null) {
        print("Time Out")
    }
}

// suspend mean that this block will run in coroutiens block
private suspend fun doWork() {
    delay(1500)
}

private fun CoroutineScope.createJobs(jobs: ArrayList<Job>) {
    jobs += launch {
        println("          default : In Thread ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) {
        println("          coroutineContext : In Thread ${Thread.currentThread().name}")
    }
    jobs += launch(newSingleThreadContext("new thread")) {
        println("          new thread : In Thread ${Thread.currentThread().name}")
    }
}