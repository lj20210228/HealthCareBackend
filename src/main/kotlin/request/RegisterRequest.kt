package com.example.request

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.SelectedDoctor
import com.example.domain.User
import kotlinx.serialization.Serializable


/**
 *
 * Parametri za registraciju korisnika, i za pacijente i za lekare, nisu svi atributi obavezni
 * @author Lazar Janković
 *
 * @property user Objekat klase [UserRequest] koji sadrzi podatke od korisniku koji se registruje,
 * nezavisno od role
 * @property doctor Objekat klase [DoctorRequest] ukoliko je role [Role.ROLE_DOCTOR] prosledjuju se podaci za lekara
 * @property patient Objekat klase [PatientRequest], ukoliko je role [Role.ROLE_PATIENT] prosledjuju se podaci za pacijenta
 * @property selectedDoctor Objekat klase [SelectedDoctor] koji bira izabranog lekara za odredjenog pacijenta
 * @see Patient
 * @see Doctor
 * @see Role
 * @see SelectedDoctor
 */



@Serializable
data class RegisterRequest(
    val user: UserRequest,
    val doctor: DoctorRequest?=null,
    val patient: PatientRequest?=null,
    val selectedDoctor: SelectedDoctor?=null
)