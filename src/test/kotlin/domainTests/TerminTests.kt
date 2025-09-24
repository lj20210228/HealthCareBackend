package com.example.domainTests

import com.example.domain.Termin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

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


}