package com.example.service

import com.example.domain.DayInWeek
import com.example.domain.WorkTime
import com.example.service.workTime.WorkTimeInterface
import com.example.service.workTime.WorkTimeServiceImplementation
import io.ktor.http.buildUrl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WorkingTimeServiceTests {

    private var service: WorkTimeInterface?=null
    private var workTime: WorkTime?=null
    var file: File?=null
    @BeforeEach
    fun setUp(){
        service= WorkTimeServiceImplementation()
        file=File("workTime.json")
        file?.writeText("")
        workTime= WorkTime("1", LocalTime.of(8,0), LocalTime.of(15,0),"1", DayInWeek.MONDAY)

    }
    @AfterEach
    fun tearDown(){
        service=null
        workTime=null
        file?.writeText("")

    }
    @Test
    fun getWorkTimeForDoctorId_test(){
        runBlocking {
            service?.addWorkTime(workTime)
            assertTrue(service?.getWorkTimeForDoctor("1")?.contains(workTime)==true)

        }
    }
    @Test
    fun addWorkTime_test_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addWorkTime(null)
            }
        }
    }
    @Test
    fun addWorkTime_test_VecPostoji(){
        runBlocking {
            service?.addWorkTime(workTime)

            assertThrows<IllegalArgumentException> {
                service?.addWorkTime(workTime)
            }
        }
    }
    @Test
    fun addWorkTime_test_ispravno(){
        runBlocking {
            service?.addWorkTime(workTime)

            assertTrue(service?.getWorkTimeForDoctor("1")?.contains(workTime)==true)
        }
    }



}