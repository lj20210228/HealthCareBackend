package com.example.service

import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface

class UserServiceImplementationtest: UserServiceInterfaceTest() {
    override fun getInstance(): UserServiceInterface {
        return UserServiceImplementation()
    }
}