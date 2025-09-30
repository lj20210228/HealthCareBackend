package com.example.repository.user

import com.example.domain.Role
import com.example.domain.User
import com.example.response.BaseResponse
import com.example.response.ListResponse


/**
 * Interfejs koji sluzi za rukovanje podacima o [User] na logickom sloju
 * @see ListResponse
 * @see BaseResponse
 * @see com.example.service.user.UserServiceInterface
 * @see User
 * @author Lazar Janković
 *
 */
interface UserRepository {
    /**
     * Funckija koja salje servisnom sloju zahtev za dodavanje novog [User]
     * @param user Korisnik kog treba dodati
     * @return [BaseResponse<User>] Vracaju se podaci o useru ako je uspesno dodat u suprotnom poruka o gresci,
     * ako vec postoji ili nije dodat
     */
    suspend fun addUser(user: User?): BaseResponse<User>

    /**
     * Funkcija koja sluzi za slanje zahteva servisnom sloju pretrazivanje usera po id
     * @param id Id usera
     * @return [BaseResponse<User>] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njemu,
     * ukoliko ne postoji vraca se poruka o gresci
     */
    suspend fun getUserById(id: String?): BaseResponse<User>
    /**
     * Funkcija koja sluzi za slanje zahteva pretrazivanje usera po [email]
     * @param email Email usera
     * @return [User] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njemu,
     * ukoliko ne postoji ili je poslat pogresan email vraca se poruka o gresci
     */
    suspend fun getUserByEmail(email: String?): BaseResponse<User>
    /**
     * Funkcija koja sluzi za slanje zahteva za pretrazivanje pretrazivanje usera po [role]
     * @param role Rola usera
     * @return [User] Ukoliko je prosledjeni argument ispravan  i User postoji vracaju se podaci o njima u listi,
     * ukoliko ne postoji vraca se greska
     */
    suspend fun getUsersForRole(role: Role?): ListResponse<User>

}
