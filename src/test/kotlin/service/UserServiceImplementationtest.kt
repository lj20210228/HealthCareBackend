package com.example.service

import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface

/**
 * Klasa koja nasledjuje klasu [UserServiceInterfaceTest]
 * kako bi implementirala njene apstrakte metode
 * @author Lazar Janković
 * @see UserServiceInterfaceTest
 * @see UserServiceImplementation
 */
class UserServiceImplementationtest: UserServiceInterfaceTest() {
    /**
     * Metoda koja vraca instancu klase [UserServiceImplementation]
     * ,gde su implementirane metode [UserServiceInterface]
     */
    override fun getInstance(): UserServiceInterface {
        return UserServiceImplementation()
    }
}