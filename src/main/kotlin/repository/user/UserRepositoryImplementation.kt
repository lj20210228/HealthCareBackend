package com.example.repository.user

import com.example.domain.Role
import com.example.domain.User
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.user.UserServiceInterface
import org.jetbrains.exposed.sql.javatime.dateParam

class UserRepositoryImplementation(val service: UserServiceInterface): UserRepository {
    override suspend fun addUser(user: User?): BaseResponse<User> {
        if (user==null)
            return BaseResponse.ErrorResponse("Niste prosledili podatke o korisniku")
        val list=service.getUsers()
        if (list.contains(user))
            return BaseResponse.ErrorResponse("Korisnik vec postoji")
        val userAdded=service.addUser(user)
        if (userAdded==null)
            return BaseResponse.ErrorResponse("Korisnik nije uspesno dodat")

        return BaseResponse.SuccessResponse(message = "Korisnik uspesno dodat", data=userAdded)
    }

    override suspend fun getUserById(id: String?): BaseResponse<User> {
        if (id==null){
            return BaseResponse.ErrorResponse(message = "Niste prosledili ispravne podatke")
        }
        val user=service.getUserById(id)
        if (user==null)
            return BaseResponse.ErrorResponse(message = "Korisnik nije pronadjen")
        return BaseResponse.SuccessResponse(data = user, message = "Korisnik uspesno pronadjen")
    }

    override suspend fun getUserByEmail(email: String?): BaseResponse<User> {
        if (email==null){
            return BaseResponse.ErrorResponse(message = "Niste prosledili ispravan")
        }
        val user=service.getUserByEmail(email)
        if (user==null)
            return BaseResponse.ErrorResponse(message = "Korisnik nije pronadjen")
        return BaseResponse.SuccessResponse(data = user, message = "Korisnik uspesno pronadjen")
    }

    override suspend fun getUsersForRole(role: Role?): ListResponse<User> {
        if (role==null){
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke")
        }
        val users=service.getUsersForRole(role)
        if (users.isEmpty())
            return ListResponse.ErrorResponse(message = "Korisnici nisu pronadjeni")
        return ListResponse.SuccessResponse(data = users, message = "Korisnici uspesno pronadjeni")
    }

}