package com.example.request

import kotlinx.serialization.Serializable

/**
 * Request koji se koristi za dodavanje pacijenta, razlikuje se od [com.example.domain.Patient] klase
 * po tome sto nema id jer ga baza sama generise, da ne bi dolazilo do gresaka
 * @see com.example.domain.Patient
 * @author Lazar Janković
 */
@Serializable
data class PatientRequest(
    val fullName: String,
    val userId: String?=null,
    val hospitalId: String,
    val jmbg: String
)
