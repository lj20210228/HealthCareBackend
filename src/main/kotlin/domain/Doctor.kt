package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    private var id: String,
    private var userId: String,
    private var fullName:String,
    private var specialization: String,
    private var maxPatients:Int,
    private var currentPatients:Int=0,
    private  var hospitalId: String,
    private var isGeneral: Boolean=false
)
