package com.example.service.user

import com.example.domain.Doctor
import com.example.domain.Role
import com.example.domain.User
import kotlinx.serialization.json.Json
import org.checkerframework.checker.mustcall.qual.MustCallAlias
import java.io.File

/**
 * Klasa koja implementira metode [UserServiceInterface]
 * @author Lazar Janković
 * @see User
 * @see UserServiceInterface
 * @property file Fajl koji sluzi za skladistenje json podataka za usera, simulira bazu
 * @property list Lista u koju se smestaju podaci o useru, koji se kasnije prevode u json ili iz jsona,
 * komunikacija sa bazom
 */
class UserServiceImplementation: UserServiceInterface {
    val file= File("users.json")
    val list=getUsers()

    /**
     * Dodavanje usera
     */
    override suspend fun addUser(user: User?): User {
        if (user==null)
            throw NullPointerException("Podaci o korisniku ne mogu biti null")
        if (list.contains(user))
            throw IllegalArgumentException("Korisnik vec postoji")
        list.add(user)
        val jsonString=Json { prettyPrint=true }.encodeToString(list)
        file.writeText(jsonString)
        return user
    }

    /**
     * Pretrazivanje usera po id
     */
    override suspend fun getUserById(id: String?): User? {
        if(id==null)
            throw NullPointerException("Id usera ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id usera ne moze biti prazan string")
        return list.find {
            it.getId()==id
        }

    }
    /**
     * Pretrazivanje usera po email
     */
    override suspend fun getUserByEmail(email: String?): User? {
        if(email==null)
            throw NullPointerException("Email usera ne moze biti null")
        if(email.isEmpty())
            throw IllegalArgumentException("Email usera ne moze biti prazan string")
        return list.find {
            it.getEmail()==email
        }
    }
    /**
     * Pretrazivanje usera po ulozi
     */
    override suspend fun getUsersForRole(role: Role?): List<User> {
        if(role==null)
            throw NullPointerException("Uloga usera ne moze biti null")

        return list.filter {
            it.getRole()==role
        }
    }

    /**
     * Funkcija koja cita podatke iz json fajla i pretvara ih u [MutableList[User]]
     */
    fun getUsers(): MutableList<User>{
        if(!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<User>>(jsonString)

    }
}