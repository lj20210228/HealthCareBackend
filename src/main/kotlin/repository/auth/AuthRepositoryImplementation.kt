package com.example.repository.auth

import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.SelectedDoctor
import com.example.repository.doctor.DoctorRepository
import com.example.repository.patient.PatientRepository
import com.example.repository.selectedDoctor.SelectedDoctorRepository
import com.example.repository.user.UserRepository
import com.example.request.LoginRequest
import com.example.request.RegisterRequest
import com.example.response.BaseResponse
import com.example.response.RegisterResponse
import com.example.security.JwtConfig
import com.example.security.hashPassword
import com.example.security.verifyPassword
import io.ktor.network.selector.SelectorManager

class AuthRepositoryImplementation(val userService: UserRepository,val doctorService: DoctorRepository,
   val jwtConfig: JwtConfig,val patientService: PatientRepository): AuthRepository {
    override suspend fun registerUser(registerParams: RegisterRequest?): BaseResponse<RegisterResponse> {

        if (registerParams == null) {
            return BaseResponse.ErrorResponse(message = "Niste uneli podatke za registraciju")
        } else {


            if (registerParams.user.getRole() == Role.ROLE_PATIENT) {
                if (registerParams.patient == null) {
                    return BaseResponse.ErrorResponse(message = "Niste uneli podatke o pacijentu")
                }
                val user = userService.addUser(registerParams.user)
                println(user)
                if (user is BaseResponse.ErrorResponse)
                    return BaseResponse.ErrorResponse(user.message)
                val userAdded = (user as BaseResponse.SuccessResponse).data

                val token = jwtConfig.createAccessToken(userAdded?.getId()!!)

                val patient = patientService.addPatient(registerParams.patient.copy(userId = userAdded.getId()))
                val patientAdded =
                    (patient as BaseResponse.SuccessResponse).data?.copy(userId = userAdded.getId())



                return BaseResponse.SuccessResponse(
                    data = RegisterResponse(
                        user = userAdded,
                        token = token,
                        patient = patientAdded,
                    )
                )

            }
            if (registerParams.user.getRole() == Role.ROLE_DOCTOR) {

                if (registerParams.doctor == null) {
                    return BaseResponse.ErrorResponse(message = "Niste uneli podatke o lekaru")
                }
                val user = userService.addUser(registerParams.user)
                if (user is BaseResponse.ErrorResponse)
                    return BaseResponse.ErrorResponse(user.message)
                val userAdded = (user as BaseResponse.SuccessResponse).data

                val token = jwtConfig.createAccessToken(userAdded?.getId()!!)
                val doctor = registerParams.doctor.copy(userId = userAdded.getId())
                val doctorAdded = doctorService.addDoctor(doctor)
                if (doctorAdded is BaseResponse.SuccessResponse) {
                    return BaseResponse.SuccessResponse(
                        data = RegisterResponse(
                            token = token,
                            user = userAdded,
                            doctor = doctorAdded.data
                        ),
                        message = "Uspesno ste se registrovali"
                    )
                }

            }
        }
        return BaseResponse.ErrorResponse("Ne mozete da se registrujete jer niste ni lekar ni pacijent")
    }

    override suspend fun loginUser(loginRequest: LoginRequest?): BaseResponse<RegisterResponse> {
        if (loginRequest==null)
            return  BaseResponse.ErrorResponse( message = "Email i lozinka ne smeju biti prazni")
        val user=userService.getUserByEmail(loginRequest.email)
        if (user is BaseResponse.ErrorResponse)
            return BaseResponse.ErrorResponse(message = "Neuspesno logovanje")
        val userGet=(user as BaseResponse.SuccessResponse).data
        println("Baza: '${userGet?.getPassword()}'")
        println("Hash inputa: '${hashPassword(loginRequest.password)}'")
        if (!verifyPassword(loginRequest.password, userGet?.getPassword()!!)) {
            return BaseResponse.ErrorResponse(message = "Neispravna lozinka")
        }
        val token=jwtConfig.createAccessToken(userGet?.getId()!!)
        return BaseResponse.SuccessResponse(
            data = RegisterResponse(
                user=userGet,
                token=token
            ), message = "Uspesno ste se ulogovali"
        )


    }
}