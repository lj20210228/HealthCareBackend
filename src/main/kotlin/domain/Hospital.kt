package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
data class Hospital(
    private var id: String,
    private var name: String,
    private var city: String,
    private var address: String
)