package com.example.service.user

import com.example.database.DatabaseFactory
import com.example.database.UserTable
import com.example.domain.Doctor
import com.example.domain.Role
import com.example.domain.User
import com.example.request.UserRequest
import com.example.security.hashPassword
import io.ktor.client.plugins.UserAgent
import kotlinx.serialization.json.Json
import org.checkerframework.checker.mustcall.qual.MustCallAlias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.Statement
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira metode [UserServiceInterface]
 * @author Lazar Janković
 * @see User
 * @see UserServiceInterface
 * komunikacija sa bazom
 */
class UserServiceImplementation: UserServiceInterface {


    /**
     * Dodavanje usera
     */
    override suspend fun addUser(user: User?): User? {

        if (user==null)
            throw NullPointerException("Podaci o korisniku ne mogu biti null")
         return DatabaseFactory.dbQuery {
            UserTable.insertReturning{
                it[email]=user.getEmail()
                it[password]= hashPassword(user.getPassword())
                it[role]=user.getRole()

            }.map{
                rowToUser(it)
            }.firstOrNull()
        }





    }


    /**
     * Pretrazivanje usera po id
     */
    override suspend fun getUserById(id: String?): User? {
        if(id==null)
            throw NullPointerException("Id usera ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id usera ne moze biti prazan string")
        val user= DatabaseFactory.dbQuery {
            UserTable.selectAll().where( UserTable.id eq UUID.fromString(id)).firstOrNull()
        }
        return rowToUser(user)

    }
    /**
     * Pretrazivanje usera po email
     */
    override suspend fun getUserByEmail(email: String?): User? {
        if(email==null)
            throw NullPointerException("Email usera ne moze biti null")
        if(email.isEmpty())
            throw IllegalArgumentException("Email usera ne moze biti prazan string")
        val user= DatabaseFactory.dbQuery {
            UserTable.selectAll().where( UserTable.email eq email).firstOrNull()
        }
        return rowToUser(user)
    }
    /**
     * Pretrazivanje usera po ulozi
     */
    override suspend fun getUsersForRole(role: Role?): List<User> {
        if(role==null)
            throw NullPointerException("Uloga usera ne moze biti null")

        val users= DatabaseFactory
            .dbQuery {
                UserTable.selectAll().where(UserTable.role eq role)
            }.mapNotNull {
                rowToUser(it)
            }

        return users

    }

    override suspend fun getUsers(): List<User> {
        return DatabaseFactory.dbQuery {
            UserTable.selectAll()
                .mapNotNull { rowToUser(it) }
        }
    }

    /**
     * Funkcija koja transformise polje iz tabele users u [User] objekat
     * @param row Polje u tabeli user
     * @return [User] Ako je user pronadjen ili null ako nije
     */
    fun rowToUser(row: ResultRow?): User?{
        if(row==null)
            return null
        else return User(
            id=row[UserTable.id].toString(),
            email = row[UserTable.email],
            password = row[UserTable.password],
            role=row[UserTable.role]
        )
    }

}