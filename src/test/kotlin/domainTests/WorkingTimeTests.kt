package com.example.domainTests

import com.example.domain.DayInWeek
import com.example.domain.WorkTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.IllegalArgumentException
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test klasa za klasu [WorkTime]
 * @author Lazar Janković
 * @see WorkTime
 * @property workTime Instanca klasa koja ce se koristiti u testovima
 */
class WorkingTimeTests {

    private var workTime: WorkTime?=null

    /**
     * Funckija koja inicijalizuje [workTime] i pokrece se pre svakog testa
     */
    @BeforeEach
    fun setUp(){
        workTime= WorkTime("1", LocalTime.of(8,0), LocalTime.of(15,0),"1", DayInWeek.MONDAY)
    }
    /**
     * Funckija koja inicijalizuje [workTime] na null i pokrece se posle svakog testa
     */
    @AfterEach
    fun tearDown(){
        workTime=null
    }

    /**
     * Test za funkciju getId, prolazi ako je vraceni id jednak dodeljenom
     */
    @Test
    fun getId_test(){
        assertEquals("1",workTime?.getId())
    }
    /**
     * Test za funkciju setId, kada se prosledi null vrednost, prolazi ako je bacen izuzetak
     * @throws NullPointerException
     */
    @Test
    fun setId_test_null(){
        assertThrows<NullPointerException> {
            workTime?.setId(null)
        }
    }
    /**
     * Test za funkciju setId, kada se prosledi prazan string, prolazi ako je bacen izuzetak
     * @throws IllegalArgumentException
     */
    @Test
    fun setId_test_prazan(){
        assertThrows<IllegalArgumentException> {
            workTime?.setId("")
        }
    }
    /**
     * Test za funkciju setId, prolazi ako je vraceni id jednak dodeljenom
     */
    @Test
    fun setId_testIspravno(){
        workTime?.setId("2")
        assertEquals("2",workTime?.getId())

    }
    /**
     * Test za funkciju getDoctorId, prolazi ako je vraceni id jednak dodeljenom
     */
    @Test
    fun getDoctorId_test(){
        assertEquals("1",workTime?.getDoctorId())
    }
    /**
     * Test za funkciju setDoctorId, kada se prosledi null vrednost, prolazi ako je bacen izuzetak
     * @throws NullPointerException
     */
    @Test
    fun setDoctorId_test_null(){
        assertThrows<NullPointerException> {
            workTime?.setId(null)
        }
    }
    /**
     * Test za funkciju setDoctorId, kada se prosledi prazan string, prolazi ako je bacen izuzetak
     * @throws IllegalArgumentException
     */
    @Test
    fun setDoctorId_test_prazan(){
        assertThrows<IllegalArgumentException> {
            workTime?.setDoctorId("")
        }
    }
    /**
     * Test za funkciju setDoctorId, prolazi ako je vraceni id jednak dodeljenom
     */
    @Test
    fun setDoctorId_testIspravno(){
        workTime?.setDoctorId("2")
        assertEquals("2",workTime?.getDoctorId())

    }

    /**
     * Test za funkciju getStartTime, prolazi ako je vracena vrednost jednaka dodeljenoj
     */
    @Test
    fun getStartTime_test(){
        assertEquals(LocalTime.of(8,0),workTime?.getStartTime())

    }

    /**
     * Test za funkciju setStartTime kada je prosledjena null vrednost
     * @throws kotlin.IllegalArgumentException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun setStartTime_test_null(){
        assertThrows<IllegalArgumentException> {
            workTime?.setStartTime(null)
        }
    }

    /**
     * Test za funkciju setStartTime kada je prosledjena vrednost koja je posle kraja radnog vremena
     *@throws IllegalArgumentException AKo se baci izuzetak test prolazi
     *
     */
    @ParameterizedTest
    @CsvSource(
        "20:00",
        "21:11",

    )
    fun setStartTime_loseVreme(startTime: String){
        assertThrows <IllegalArgumentException>{
            workTime?.setStartTime(LocalTime.parse(startTime))
        }
    }

    /**
     * Test za setStartTime kada su prosledjene ispravne vrednosti, ako je prosledjena vrednost jednaka dodeljenoj test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "10:00",
        "09:11",
        "07:11"

    )
    fun setStartTime_ispravno(startTime: String){

        workTime?.setStartTime(LocalTime.parse(startTime))
        assertEquals(startTime,workTime?.getStartTime().toString())
    }
    /**
     * Test za funkciju getEndTime, prolazi ako je vracena vrednost jednaka dodeljenoj
     */
    @Test
    fun getEndTime_test(){
        assertEquals(LocalTime.of(15,0),workTime?.getEndTime())

    }
    /**
     * Test za funkciju setEndTime kada je prosledjena null vrednost
     * @throws kotlin.IllegalArgumentException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun setEndTime_test_null(){
        assertThrows<IllegalArgumentException> {
            workTime?.setEndTime(null)
        }
    }
    /**
     * Test za funkciju setEndTime kada je prosledjena vrednost koja je pre pocetka radnog vremena
     * @throws IllegalArgumentException AKo se baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "06:11",
        "07:11"

    )
    fun setEndTime_loseVreme(endTime: String){
        assertThrows <IllegalArgumentException>{
            workTime?.setEndTime(LocalTime.parse(endTime))
        }
    }
    /**
     * Test za setEndTime kada su prosledjene ispravne vrednosti, ako je prosledjena vrednost jednaka dodeljenoj test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "20:00",
        "21:11"
    )
    fun setEndTime_ispravno(endTime: String){

        workTime?.setEndTime(LocalTime.parse(endTime))
        assertEquals(endTime,workTime?.getEndTime().toString())
    }

    /**
     * Test za funkciju getDayInWeek, ako je vracena vrednost jednaka dodeljenoj test prolazi
     */
    @Test
    fun getDayInWeek_test(){
        assertEquals(DayInWeek.MONDAY,workTime?.getDayIn())
    }

    /**
     * Test za funkciju getDayInWeek
     * @throws NullPointerException ako se baci izuzetak test prolazi
     */
    @Test
    fun setDayInWeek_testNull(){
        assertThrows<NullPointerException> {
            workTime?.setDayIn(null)
        }
    }
    /**
     * Test za funkciju setDayInWeek, ako je vracena vrednost jednaka dodeljenoj test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
        "SUNDAY"
    )
    fun setDayInWeek_testIspravno(day: String){
        workTime?.setDayIn(DayInWeek.valueOf(day))
        assertEquals(day,workTime?.getDayIn().toString())
    }

    /**
     * Test za metodu equals, prosledjuju joj se razliciti parametri i ocekivana vrednost,
     * jedino ako su isti id-evi onda je true, u suprotnom false
     */
    @ParameterizedTest
    @CsvSource(
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc1,MONDAY,true",
        "1,09:00,17:00,doc1,MONDAY,2,09:00,17:00,doc1,MONDAY,false",
        "1,09:00,17:00,doc1,MONDAY,1,10:00,18:00,doc1,MONDAY,true",
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc2,MONDAY,true",
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc1,TUESDAY,true"
    )
    fun equals_test(
        id1: String, start1: String, end1: String, doctorId1: String, day1: String,
        id2: String, start2: String, end2: String, doctorId2: String, day2: String,
        expected: Boolean
    ) {
        val workTime1= WorkTime(
            id1, LocalTime.parse(start1), LocalTime.parse(end1),doctorId1, DayInWeek.valueOf(day1)
        )
        val workTime2= WorkTime(
            id2, LocalTime.parse(start2), LocalTime.parse(end2),doctorId2, DayInWeek.valueOf(day2)
        )
        assertEquals(expected,workTime1.equals(workTime2))

    }

    /**
     * Test za metodu hashCode, ako su za 2 objekta hashCode jednaki vraca test prolazi,
     * hash zavisi samo od id
     */
    @ParameterizedTest
    @CsvSource(
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc1,MONDAY",
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc1,MONDAY",
        "1,09:00,17:00,doc1,MONDAY,1,10:00,18:00,doc1,MONDAY",
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc2,MONDAY",
        "1,09:00,17:00,doc1,MONDAY,1,09:00,17:00,doc1,TUESDAY"
    )
    fun hashCodeTest(
        id1: String, start1: String, end1: String, doctorId1: String, day1: String,
        id2: String, start2: String, end2: String, doctorId2: String, day2: String,
    ){
        val workTime1= WorkTime(
            id1, LocalTime.parse(start1), LocalTime.parse(end1),doctorId1, DayInWeek.valueOf(day1)
        )
        val workTime2= WorkTime(
            id2, LocalTime.parse(start2), LocalTime.parse(end2),doctorId2, DayInWeek.valueOf(day2)
        )
        assertEquals(workTime1.hashCode(),workTime2.hashCode())
    }

    /**
     * Test za metodu toString, ako sadrzi sve elemente, test prolazi
     */
    @Test
    fun toString_test(){
        val string=workTime.toString()
        assertTrue(string.contains("1"))
        assertTrue(string.contains("1"))
        assertTrue(string.contains("08:00"))
        assertTrue(string.contains("15:00"))
        assertTrue(string.contains("MONDAY"))
    }





}