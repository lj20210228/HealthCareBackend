package com.example.request

import com.example.domain.Role
import kotlinx.serialization.Serializable

/**
 * Request koji se koristi za dodavanje usera, razlikuje se od [com.example.domain.User] klase
 * po tome sto nema id jer ga baza sama generise, da ne bi dolazilo do gresaka
 * @see com.example.domain.User
 * @author Lazar Janković
 */
@Serializable
data class UserRequest(
    val email:String,
    val password:String,
    val role: Role
)
