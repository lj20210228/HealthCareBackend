package com.example.service.user

import com.example.domain.Role
import com.example.domain.User
import com.example.request.UserRequest

/**
 * Interfejs koji sluzi za rukovanje podacima o [User]
 * @author Lazar Janković
 * @see User
 */
interface UserServiceInterface {
    /**
     * Funckija koja sluzi za dodavanje novog [User] u tabelu
     * @param user Korisnik kog treba dodati
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @throws IllegalArgumentException Ukoliko prosledjeni argument vec postoji
     * @return [User] Vracaju se podaci o useru ako je uspesno dodat u suprotnom null
     */
    suspend fun addUser(user: UserRequest?): User?

    /**
     * Funkcija koja sluzi za pretrazivanje usera po id
     * @param id Id usera
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @throws IllegalArgumentException Ukoliko je prosledjeni argument prazan string
     * @return [User] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njemu,
     * ukoliko ne postoji null
     */
    suspend fun getUserById(id: String?): User?
    /**
     * Funkcija koja sluzi za pretrazivanje usera po [email]
     * @param email Email usera
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @throws IllegalArgumentException Ukoliko je prosledjeni argument prazan string
     * @return [User] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njemu,
     * ukoliko ne postoji null
     */
    suspend fun getUserByEmail(email: String?): User?
    /**
     * Funkcija koja sluzi za pretrazivanje usera po [role]
     * @param role Rola usera
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @return [User] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njima u listi,
     * ukoliko ne postoji vraca se prazna lista
     */
    suspend fun getUsersForRole(role: Role?): List<User>
}
