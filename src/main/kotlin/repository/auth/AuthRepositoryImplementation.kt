package com.example.repository.auth

import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.User
import com.example.request.RegisterRequest
import com.example.response.BaseResponse
import com.example.response.RegisterResponse
import com.example.security.JwtConfig
import com.example.service.DoctorServiceInterface
import com.example.service.patient.PatientServiceInterface
import com.example.service.selectedDoctor.SelectedDoctorServiceInterface
import com.example.service.user.UserServiceInterface

class AuthRepositoryImplementation(val userService: UserServiceInterface,val doctorService: DoctorServiceInterface,
    val selectedDoctorService: SelectedDoctorServiceInterface,val jwtConfig: JwtConfig,val patientService: PatientServiceInterface): AuthRepository {
    override suspend fun registerUser(registerParams: RegisterRequest?): BaseResponse<RegisterResponse> {
        if (registerParams==null){
            return BaseResponse.ErrorResponse(message = "Niste uneli podatke za registraciju")
        }else{

            val user=userService.addUser(registerParams.user)
            if(user==null)
                return BaseResponse.ErrorResponse("Korisnik nije uspesno dodat")
            val token=jwtConfig.createAccessToken(user.getId())
            if(registerParams.user.getRole()== Role.ROLE_PATIENT){
                if (registerParams.patient==null){
                    return BaseResponse.ErrorResponse(message = "Niste uneli podatke o pacijentu")
                }
                val patient=patientService.addPatient(registerParams.patient)
                val selected=selectedDoctorService.addSelectedDoctorForPatient(registerParams.selectedDoctor)
                return BaseResponse.SuccessResponse(data = RegisterResponse(
                    user=user,
                    token=token,
                    patient = patient,
                    selectedDoctor = selected
                ))

            }
            if (registerParams.user.getRole() == Role.ROLE_DOCTOR){
                if (registerParams.doctor==null){
                    return BaseResponse.ErrorResponse(message = "Niste uneli podatke o lekaru")
                }
                val doctor=doctorService.addDoctor(registerParams.doctor)
                return BaseResponse.SuccessResponse(
                    data = RegisterResponse(
                        token=token,
                        user=user,
                        doctor = doctor
                    ),
                    message = "Uspesno ste se registrovali"
                )
            }
            return BaseResponse.ErrorResponse("Ne mozete da se registrujete jer niste ni lekar ni pacijent")
        }
    }

    override suspend fun loginUser(
        email: String?,
        password: String?
    ): BaseResponse<RegisterResponse> {
        if (email.isNullOrEmpty()||password.isNullOrEmpty())
            return  BaseResponse.ErrorResponse( message = "Email i lozinka ne smeju biti prazni")
        val user=userService.getUserByEmail(email)
        if (user==null)
            return BaseResponse.ErrorResponse("Neuspesno logovanje")
        val token=jwtConfig.createAccessToken(user.getId())
        return BaseResponse.SuccessResponse(
            data = RegisterResponse(
                user=user,
                token=token
            ), message = "Uspesno ste se ulogovali"
        )


    }
}