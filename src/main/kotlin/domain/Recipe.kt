package com.example.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Recipe(
    private var id: String,
    private var patientId: String,
    private var medication: String,
    private var quantity: Int,
    @Contextual
    private var dateExpired: LocalDate
)
