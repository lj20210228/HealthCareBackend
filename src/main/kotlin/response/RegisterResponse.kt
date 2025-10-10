package com.example.response

import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.domain.User
import com.example.security.JwtConfig
import kotlinx.serialization.Serializable

/**
 * Odgovor prilikom registarcije koji vraca podatke o registrovanom korisniku
 *
 * @author Lazar Janković
 * @param user Podaci o korisniku iz user tabele
 * @param doctor Podaci o korisniku ako je lekar
 * @param patient Podaci o korisniku ako je pacijent

 */
@Serializable
data class RegisterResponse(
    val user: User,
    val doctor: Doctor?=null,
    val patient: Patient?=null,
    val token: String,

)

