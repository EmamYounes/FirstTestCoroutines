package com.example.firsttestcoroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSuspend() = runBlocking {
        doWork()
        assertEquals(1, minimumDistances(secondArray()))
    }

    private fun firstArray() = intArrayOf(7, 1, 3, 4, 1, 7).toTypedArray()
    var firstExpectation = 3
    var secondExpectation = -1
    private fun secondArray() = intArrayOf(1,1).toTypedArray()

    private suspend fun doWork() {
        delay(1500)
    }

    fun minimumDistances(a: Array<Int>): Int {
        var firstDeff = 0
        var secondDeff = 0
        for (i in 0..a.size) {
            if (i + 1 < a.size) {
                for (j in i + 1..a.size - 1) {
                    if (a[i] == a[j]) {
                        if (firstDeff == 0) {
                            firstDeff = i - j
                        } else {
                            secondDeff = i - j
                        }
                    }
                }
            }
        }

        var result = 0
        if (abs(firstDeff) >= abs(secondDeff))
            result = abs(secondDeff)
        else
            result = abs(firstDeff)
        if (result == 0) return -1
        else return result
    }
}
