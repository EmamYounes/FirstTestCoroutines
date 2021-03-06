package com.example.firsttestcoroutines

import kotlinx.coroutines.*
import java.lang.Thread.yield
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    //    printHelloWorldByCoroutiens()
//    incrementNumber()
//    printHelloWorldByCoroutiens2()
//    printHelloWorldByCoroutiens3()
//    printDote2()
//    timeOut()
//    val jobs = arrayListOf<Job>()
//    createJobs(jobs)
//    jobs.forEach { it -> it.join() }
//    printActivation()
//    parentChildRelationships()
//    newSingleThread()

//    simpleAsyncAwait()
    lazilyAsync()
}

private fun CoroutineScope.lazilyAsync() {
    val job = launch {
        val result = async(start = CoroutineStart.LAZY) {
            doWorkLazy()
        }
        println("The Result is${result.await()}")
    }
}

suspend fun doWorkLazy(): Int {
    println("Be lazy")
    delay(200)
    println("lazy done")
    return 42
}

private suspend fun CoroutineScope.simpleAsyncAwait() {
    val job = launch {
        val time = measureTimeMillis {
            val r1: Deferred<Int> = async { doWorkOne() }
            val r2: Deferred<Int> = async { doWorkTwo() }
            println(r1.await() + r2.await())
        }
        println("Done in : $time")
    }
    job.join()
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

private suspend fun CoroutineScope.printActivation() {
    val job = launch {
        print("isActive  ${coroutineContext[Job]!!.isActive}")
    }
    job.join()
}

private suspend fun CoroutineScope.parentChildRelationships() {
    val outer = launch {
        launch(coroutineContext) {
            repeat(1000) {
                print(".")
            }
            delay(1)
        }
    }
    outer.join()
    println()
    println("Outer is Finished")
}

private suspend fun CoroutineScope.newSingleThread() {
    newSingleThreadContext("SingleThreadContext").use { ctx ->
        val job = launch(ctx) {
            println("thread name  : ${Thread.currentThread().name}")
        }
        job.join()
    }
}

suspend fun doWorkOne(): Int {
    delay(100)
    println("working 1")
    return Random(System.currentTimeMillis()).nextInt(42)
}

suspend fun doWorkTwo(): Int {
    delay(100)
    println("working 2")
    return Random(System.currentTimeMillis()).nextInt(42)
}