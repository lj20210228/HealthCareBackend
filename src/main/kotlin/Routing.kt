package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.repository.auth.AuthRepositoryImplementation
import com.example.repository.doctor.DoctorRepositoryImplementation
import com.example.repository.hospital.HospitalRepositoryImplementation
import com.example.repository.patient.PatientRepositoryImplementation
import com.example.repository.selectedDoctor.SelectedDoctorRepositoryImplementation
import com.example.repository.user.UserRepositoryImplementation
import com.example.routes.authRoutes
import com.example.routes.doctorRoutes
import com.example.routes.hospitalRoutes
import com.example.routes.patientRoutes
import com.example.routes.selectedDoctorRoutes
import com.example.routes.userRoutes
import com.example.security.JwtConfig
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.selectedDoctor.SelectedDoctorServiceImplementation
import com.example.service.user.UserServiceImplementation
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRoutes(
            AuthRepositoryImplementation(
                userService = UserRepositoryImplementation(
                    service = UserServiceImplementation()
                ),
                doctorService = DoctorRepositoryImplementation(
                    DoctorServiceImplementation(),
                    HospitalServiceImplementation()
                ),
                selectedDoctorService = SelectedDoctorRepositoryImplementation(
                     service = SelectedDoctorServiceImplementation(
                         doctorServiceInterface = DoctorServiceImplementation(),
                         patientService = PatientServiceImplementation()
                     ),
                ),
                jwtConfig = JwtConfig.instance,
                patientService = PatientRepositoryImplementation(
                    service = PatientServiceImplementation(),
                    hospitalService = HospitalServiceImplementation()
                )
            )
        )
        userRoutes(repository = UserRepositoryImplementation(
            service = UserServiceImplementation()
        ))
        hospitalRoutes(HospitalRepositoryImplementation(
            HospitalServiceImplementation()
        ))
        doctorRoutes(DoctorRepositoryImplementation(
            DoctorServiceImplementation(),
            hospitalService = HospitalServiceImplementation()
        ))
        patientRoutes(PatientRepositoryImplementation(
            service = PatientServiceImplementation(),
            hospitalService = HospitalServiceImplementation()
        ))
        selectedDoctorRoutes(
            repository = SelectedDoctorRepositoryImplementation(
                service = SelectedDoctorServiceImplementation(
                    doctorServiceInterface = DoctorServiceImplementation(),
                    patientService = PatientServiceImplementation()
                )
            )
        )
    }
}
