package com.example.repositoryTests

import com.example.UserService
import com.example.database.DatabaseFactory
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.SelectedDoctor
import com.example.domain.User
import com.example.repository.auth.AuthRepository
import com.example.repository.auth.AuthRepositoryImplementation
import com.example.repository.doctor.DoctorRepositoryImplementation
import com.example.repository.patient.PatientRepositoryImplementation
import com.example.repository.selectedDoctor.SelectedDoctorRepositoryImplementation
import com.example.repository.user.UserRepositoryImplementation
import com.example.request.LoginRequest
import com.example.request.RegisterRequest
import com.example.response.BaseResponse
import com.example.response.RegisterResponse
import com.example.security.JwtConfig
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.selectedDoctor.SelectedDoctorServiceImplementation
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test za klasu [AuthRepository]
 * @see AuthRepository
 * @author Lazar Jankovic
 * @see User
 * @see Patient
 * @see Doctor
 * @see Hospital
 * @see com.example.repository.patient.PatientRepository
 * @see com.example.repository.user.UserRepository
 * @see com.example.repository.doctor.DoctorRepository
 */
class AuthRepositoryTests {

    private lateinit var user: User
    private lateinit var user2: User

    private lateinit var patient: Patient
    private lateinit var doctor: Doctor
    private lateinit var patientService: PatientServiceInterface

    private lateinit var repository: AuthRepository
    private lateinit var hospital: Hospital
    private lateinit var hospitalService: HospitalServiceInterface

    /**
     * Pre pokretanja svakog testa vrsi se povezivanje sa bazom i prazne se koloke users,doctor i patient,
     * inicijalizuju se servisi koji su nam potrebni za registraciju pacijenta i prave se objekti User,Doctor,
     * Patient i Hospital koji ce nam trebati za vreme testova
     */
    @BeforeEach
    fun setUp(){
        JwtConfig.initialize("health_care")
        DatabaseFactory.init()
        DatabaseFactory.clearTable("users")


        DatabaseFactory.clearTable("doctor")
        DatabaseFactory.clearTable("patient")
        hospitalService= HospitalServiceImplementation()
        patientService= PatientServiceImplementation()
        repository= AuthRepositoryImplementation(
            userService = UserRepositoryImplementation(
                service = UserServiceImplementation()
            ),
            doctorService = DoctorRepositoryImplementation(
                DoctorServiceImplementation(),
                hospitalService = HospitalServiceImplementation()
            ),
            jwtConfig = JwtConfig.instance,
            patientService = PatientRepositoryImplementation(
                PatientServiceImplementation(),
                hospitalService = HospitalServiceImplementation()
            )
        )

        hospital= Hospital(

            name = "Opsta bolnica Uzice",
            city = "Uzice",
            address = "Milosa Obrenovica 33"
        )
        user=User(
            email = "lazar222@test.com",
            password = "Password123!",
            role = Role.ROLE_PATIENT
        )
        user2=User(
            email = "lazar22@test.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR
        )

        runBlocking {
           hospital= hospitalService.addHospital(hospital)!!

        }
        doctor= Doctor(
            fullName = "Pera Periv",
            specialization = "Neurolog",
            maxPatients = 40,
            currentPatients = 0,
            hospitalId = hospital.getId()!!,
            isGeneral = false
        )



        patient= Patient(

            fullName = "Lazar Jankovic",
            hospitalId = hospital.getId()!!,
            jmbg = "1007002790023"
        )



    }


    /**
     * Registracija kad je prosledjena null vrednost
     */
    @Test
    fun register_null()=runBlocking {

        val registered=repository.registerUser(null)
        assertTrue(registered is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli podatke za registraciju",registered.message)
    }
    /**
     * Uspesna registracija pacijenta
     */
    @Test
    fun register_patientTestUspesno()=runBlocking{
        val registered=repository.registerUser(RegisterRequest(
            user=user,patient=patient
        ))
        assertTrue(registered is BaseResponse.SuccessResponse)
        assertEquals(user,(registered.data?.user))
        assertEquals(patient,(registered.data?.patient))

    }
    /**
     * Registracija pacijenta kad je prosledjena null vrednost za pacijenta
     */
    @Test
    fun register_patientNullTest()=runBlocking{
        val registered=repository.registerUser(RegisterRequest(
            user=user,patient=null
        ))
        assertTrue(registered is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli podatke o pacijentu",registered.message)
    }

    /**
     * Uspesna registracija lekara
     */
    @Test
    fun register_DoctorTestUspesno()=runBlocking{

        val registered=repository.registerUser(RegisterRequest(
            user=user2,doctor=doctor
        ))
        println(registered)
        assertTrue(registered is BaseResponse.SuccessResponse)
        assertEquals(user2,(registered.data?.user))
        assertEquals(doctor,(registered.data?.doctor))

    }
    /**
     * Registracija lekara kada je prosledjena null vrednost za lekara
     */
    @Test
    fun register_DoctorNullTest()=runBlocking{
        val registered=repository.registerUser(RegisterRequest(
            user=user2,doctor=null
        ))
        assertTrue(registered is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli podatke o lekaru",registered.message)
    }

    /**
     * Test za uspesno logovanje
     */
    @Test
    fun login_testUspesno()=runBlocking{
        repository.registerUser(RegisterRequest(
            user=user2,doctor=doctor
        ))
        val logined=repository.loginUser(LoginRequest(user2.getEmail(),user2.getPassword()))
        assertTrue(logined is BaseResponse.SuccessResponse)
        assertEquals(logined.data?.user,user2)

    }
    /**
     * Test za login kada su prosledjeni null parametri
     */
    @Test
    fun login_testNull()=runBlocking {
        val logined=repository.loginUser(null)
        assertTrue(logined is BaseResponse.ErrorResponse)
        assertEquals("Email i lozinka ne smeju biti prazni",logined.message)
    }

}