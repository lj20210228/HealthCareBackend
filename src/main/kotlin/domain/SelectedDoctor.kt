package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
data class SelectedDoctor(
    private var patientId: String,
    private var doctorId: String
)
