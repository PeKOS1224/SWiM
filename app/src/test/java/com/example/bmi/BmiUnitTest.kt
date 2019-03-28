package com.example.bmi

import com.example.bmi.Logic.BmiForInchesPounds
import com.example.bmi.Logic.BmiForKgCm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BmiUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun for_valid_data_return_bmi_metric(){
        val testBmi = BmiForKgCm(65, 170)
        assertEquals( 22.491,  testBmi.countBMI(), 0.001)
    }
    @Test
    fun for_valid_data_return_bmi_imperial(){
        val testBmi = BmiForInchesPounds(150, 71)
        assertEquals( 20.92,  testBmi.countBMI(), 0.005)
    }
    @Test
    fun is_my_bmi_correct(){
        val myBmi: BmiForKgCm = BmiForKgCm(59, 180)
        assertEquals( 18.209,  myBmi.countBMI(), 0.001)
    }
}
