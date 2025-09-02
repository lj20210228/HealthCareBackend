package com.example.domain

import kotlinx.serialization.Serializable


@Serializable
data class Patient(
    private var id: String,
    private var userId: String,
    private var fullName: String,
    private var hospitalId: String,
)
