package com.example.repository.auth

import com.example.domain.Role
import com.example.domain.User
import com.example.request.RegisterRequest
import com.example.response.BaseResponse
import com.example.response.RegisterResponse
import com.example.service.user.UserServiceInterface


/**
 * Interfejs koji sluzi za rukovanje podacima o [User]
 * @author Lazar Janković
 * @see UserServiceInterface
 */
interface AuthRepository {
    suspend fun registerUser(registerParams: RegisterRequest?): BaseResponse<RegisterResponse>
    suspend fun loginUser(email: String?,password: String?): BaseResponse<RegisterResponse>
}