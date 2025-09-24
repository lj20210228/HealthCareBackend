package com.example.domainTests

import com.example.domain.Termin
import com.example.domain.TerminStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TerminTests {
    private var termin: Termin?=null
    @BeforeEach
    fun setUp(){
        termin= Termin("1","1","1", LocalDate.of(2025,9,30), startTime = LocalTime.of(8,0) , hospitalId = "1")

    }
    @AfterEach
    fun tearDown(){
        termin=null
    }

    /**
     * Test za funkciju getId, ako je vraceni parametar jednak dodeljenom test prolazi
     */
    @Test
    fun getId_test(){
        assertEquals("1",termin?.getId())
    }

    /**
     * Test za metodu setId kada je prosledjen null
     * @throws NullPointerException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setId_null_test(){
        assertThrows<NullPointerException> {
            termin?.setId(null)
        }
    }
    /**
     * Test za metodu setId kada je prosledjen prazan string
     * @throws IllegalArgumentException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setId_prazan_test(){
        assertThrows<IllegalArgumentException> {
            termin?.setId("")
        }
    }
    /**
     * Test za metodu setId kada je prosledjen ispravan id
     * ,ako je prosledjeni argument jednak dodeljenom test prolazi
     */
    @Test
    fun setId_ispravno_test(){
        termin?.setId("1")

        assertEquals("1",termin?.getId())
    }
    /**
     * Test za funkciju getDoctorId, ako je vraceni parametar jednak dodeljenom test prolazi
     */
    @Test
    fun getDoctorId_test(){
        assertEquals("1",termin?.getDoctorId())
    }

    /**
     * Test za metodu setDoctorId kada je prosledjen null
     * @throws NullPointerException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setDoctorId_null_test(){
        assertThrows<NullPointerException> {
            termin?.setDoctorId(null)
        }
    }
    /**
     * Test za metodu setDoctorId kada je prosledjen prazan string
     * @throws IllegalArgumentException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setDoctorId_prazan_test(){
        assertThrows<IllegalArgumentException> {
            termin?.setDoctorId("")
        }
    }
    /**
     * Test za metodu setDoctorId kada je prosledjen ispravan id
     * ,ako je prosledjeni argument jednak dodeljenom test prolazi
     */
    @Test
    fun setDoctorId_ispravno_test(){
        termin?.setDoctorId("1")

        assertEquals("1",termin?.getDoctorId())
    }
    /**
     * Test za funkciju getPatientId, ako je vraceni parametar jednak dodeljenom test prolazi
     */
    @Test
    fun getPatientId_test(){
        assertEquals("1",termin?.getPatientId())
    }

    /**
     * Test za metodu setPatientId kada je prosledjen null
     * @throws NullPointerException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setPatientId_null_test(){
        assertThrows<NullPointerException> {
            termin?.setPatientId(null)
        }
    }
    /**
     * Test za metodu setPatientId kada je prosledjen prazan string
     * @throws IllegalArgumentException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setPatientId_prazan_test(){
        assertThrows<IllegalArgumentException> {
            termin?.setPatientId("")
        }
    }
    /**
     * Test za metodu setPatientId kada je prosledjen ispravan id
     * ,ako je prosledjeni argument jednak dodeljenom test prolazi
     */
    @Test
    fun setPatientId_ispravno_test(){
        termin?.setPatientId("1")

        assertEquals("1",termin?.getPatientId())
    }
    /**
     * Test za funkciju getHospitalId, ako je vraceni parametar jednak dodeljenom test prolazi
     */
    @Test
    fun getHospitaltId_test(){
        assertEquals("1",termin?.getHospitalId())
    }

    /**
     * Test za metodu setPatientId kada je prosledjen null
     * @throws NullPointerException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setHospitalId_null_test(){
        assertThrows<NullPointerException> {
            termin?.setHospitalId(null)
        }
    }
    /**
     * Test za metodu setHospital kada je prosledjen prazan string
     * @throws IllegalArgumentException AKo se baci izuzetak test prolazi
     */
    @Test
    fun setHospitalId_prazan_test(){
        assertThrows<IllegalArgumentException> {
            termin?.setHospitalId("")
        }
    }
    /**
     * Test za metodu setPatientId kada je prosledjen ispravan id
     * ,ako je prosledjeni argument jednak dodeljenom test prolazi
     */
    @Test
    fun setHospitalId_ispravno_test(){
        termin?.setHospitalId("1")
        assertEquals("1",termin?.getHospitalId())
    }


    /**
     * Test za funkciju getDate, ako je dodeljeni datim jednak vracenom test prolazi
     */
    @Test
    fun getDateTest(){
        assertEquals(LocalDate.of(2025,9,30),termin?.getDate())

    }

    /**
     * Test za setDate kad je prosledjen null
     * @throws NullPointerException Ako se baci izuzetak test prolazi
     */
    @Test
    fun setDate_null() {
        assertThrows<NullPointerException> {
            termin?.setDate(null)
        }
    }
    /**
     * Test za setDate kad je prosledjen datum pre danasnjeg
     * @throws IllegalArgumentException Ako se baci izuzetak test prolazi
     */
    @Test
    fun setDate_preTrenutnog(){
        assertThrows<IllegalArgumentException> {
            termin?.setDate(LocalDate.of(2025,8,30))
        }
    }
    /**
     * Test za setDate kad je prosledjen ispravan datum,
     * ako je vracen datum koji je isti kao prosledjen
     */
    @Test
    fun setDate_ispravno(){
        termin?.setDate(LocalDate.of(2025,10,1))
        assertEquals(LocalDate.of(2025,10,1),termin?.getDate())
    }

    /**
     * Test za metodu getStartTime, ako je prosledjeno vreme isto kao vraceno test prolazi
     */
    @Test
    fun getStartTime_test(){
        assertEquals(LocalTime.of(8,0),termin?.getStartTime())
    }
    /**
     * Test za metodu setStartTime kada je prosledjena null vrednost
     * @throws NullPointerException ako se baci izuzetak test prolazi
     */
    @Test
    fun setStartTime_nullTest(){
        assertThrows<NullPointerException> {
            termin?.setStartTime(null)
        }
    }
    /**
     * Test za metodu setStartTime kada je prosledjeno vreme posle [endTime]
     * @throws NullPointerException ako se baci izuzetak test prolazi
     */
    @Test
    fun setStartTime_posleZavrsnogTest(){
        assertThrows<IllegalArgumentException> {
            termin?.setStartTime(LocalTime.of(12,0))
        }
    }

    /**
     * Test za metodu setStartTime kada je prosledjeno ispravno vreme, ako je vraceno vreme jednako prosledjenom test prolazi
     */
    @Test
    fun setStartTime_ispravnoTest(){
        termin?.setStartTime(LocalTime.of(9,0))
        assertEquals(LocalTime.of(9,0),termin?.getStartTime())
    }
    /**
     * Test za metodu getEndTime, ako je prosledjeno vreme isto kao vraceno test prolazi
     */
    @Test
    fun getEndTime_test(){
        assertEquals(LocalTime.of(9,0),termin?.getEndTime())
    }
    /**
     * Test za metodu setEndTime kada je prosledjena null vrednost
     * @throws NullPointerException ako se baci izuzetak test prolazi
     */
    @Test
    fun setEndTime_nullTest(){
        assertThrows<NullPointerException> {
            termin?.setEndTime(null)
        }
    }
    /**
     * Test za metodu setEndTime kada je prosledjeno vreme pre [startTime]
     * @throws NullPointerException ako se baci izuzetak test prolazi
     */
    @Test
    fun setEndtTime_posleZavrsnogTest(){
        assertThrows<IllegalArgumentException> {
            termin?.setEndTime(LocalTime.of(1,0))
        }
    }

    /**
     * Test za metodu setEndTime kada je prosledjeno ispravno vreme, ako je vraceno vreme jednako prosledjenom test prolazi
     */
    @Test
    fun setEndTime_ispravnoTest(){
        termin?.setEndTime(LocalTime.of(9,0))
        assertEquals(LocalTime.of(9,0),termin?.getEndTime())
    }

    /**
     * Test za getStatus
     */
    @Test
    fun getStatus_test(){
        assertEquals(TerminStatus.PENDING,termin?.getTerminStatus())
    }

    /**
     * test za setStatus kad je ispravno prosledjen
     */
    @Test
    fun setStatus_testIspravno(){
        termin?.setTerminStatus(TerminStatus.ON_HOLD)
        assertEquals(TerminStatus.ON_HOLD,termin?.getTerminStatus())
    }
    /**
     * test za setStatus kad je  prosledjen null
     */
    @Test
    fun setStatus_testnull(){

        assertThrows < NullPointerException>{
            termin?.setTerminStatus(null)
        }
    }


    /**
     * Test za equals
     */
    @ParameterizedTest
    @CsvSource
        (
                "1,1,1,1,2025-09-30,08:00,09:00,ON_HOLD,1,1,1,1,2025-09-30,08:00,09:00,ON_HOLD,true",
        "1,1,1,1,2025-09-30,08:00,09:00,ON_HOLD,1,1,1,1,2025-09-30,08:00,09:00,PENDING,true",
        "2,1,1,1,2025-09-30,08:00,09:00,ON_HOLD,1,1,1,1,2025-09-30,08:00,09:00,PENDING,false"
                )
    fun equals_test(
        id1:String,hospitalId1: String,doctorId1: String,patientId1: String,date1: LocalDate,start1: LocalTime,end1: LocalTime,status1: TerminStatus,
        id2:String,hospitalId2: String,doctorId2: String,patientId2: String,date2: LocalDate,start2: LocalTime,end2: LocalTime,status2: TerminStatus,bool: Boolean
    ){
        val termin1= Termin(
            id = id1, hospitalId = hospitalId1, doctorId = doctorId1,
            patientId = patientId1,
            date = date1,
            startTime = start1,
            endTime = end1,
            status = status1,
        )
        val termin2= Termin(
            id = id2, hospitalId = hospitalId2, doctorId = doctorId2,
            patientId = patientId2,
            date = date2,
            startTime = start2,
            endTime = end2,
            status = status2,
        )
        assertEquals(bool,termin1.equals(termin2))
    }
    /**
     * Test za toString
     */
    @Test
    fun toString_test(){
        val string=termin?.toString()
        assertTrue(string?.contains("1")!!)
        assertTrue(string?.contains("1")!!)

        assertTrue(string?.contains("1")!!)

        assertTrue(string?.contains("1")!!)

        assertTrue(string?.contains("2025-09-30")!!)
        assertTrue(string?.contains("08:00")!!)
        assertTrue(string?.contains("09:00")!!)
    }
    /**
     * Test za hashCode
     */
    @Test
    fun hashCode_test(){
        val termin1= Termin("1","1","1", LocalDate.of(2025,9,30), startTime = LocalTime.of(8,0) , hospitalId = "1")
        assertEquals(termin1.hashCode(),termin.hashCode())
    }



}