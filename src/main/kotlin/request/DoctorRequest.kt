package com.example.request

import kotlinx.serialization.Serializable



/**
 * Request koji se koristi za dodavanje lekara, razlikuje se od [com.example.domain.Doctor] klase
 * po tome sto nema id jer ga baza sama generise, da ne bi dolazilo do gresaka
 * @see com.example.domain.Doctor
 * @author Lazar Janković
 */
@Serializable
data class DoctorRequest(
    val fullName: String,
    val specialization:String,
    val isGeneral: Boolean=false,
    val maxPatients: Int,
    val currentPatients:Int=0,
    val hospitalId: String,
    val userId: String?=null
)
