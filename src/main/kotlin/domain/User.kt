package com.example.domain

import com.typesafe.config.ConfigException
import kotlinx.serialization.Serializable

/**
 * Klasa koja sluzi za registraciju i ulogovanje pacijenata u aplikaciju, sadrzi osnovne podatke o Useru,
 * klasa je Serializable kako bi mogla da se prevede u JSON objekat
 *
 * @author Lazar Janković
 * @param id Id korisnika u User tabeli, treba da bude jedinstven za svakoga
 * @param email Email korisnika, treba da bude jedinstven za svakoga
 * @param password Lozinka za korisnicki nalog
 * @param role Instanca [Role] klase, moze biti lekar ili pacijent
 *
 */
@Serializable
data class User(
    private var id: String,
    private var email: String,
    private var password: String,
    private var role: Role
){
    /**
     * Metoda koja sluzi za inicijalizaciju objekta klase [User],
     * postavlja njegove vrednosti na prosledjene, i pri tome hvata greske ukoliko postoje
     */
    init {
        setId(id)
        setEmail(email)
        setPassword(password)
        setRole(role)
    }
    /**
     * Metoda koja vraca id korisnika
     * @return String koji vraca [id] korisnika
     */
    fun getId(): String{
        return this.id
    }


    /**
     * Metoda za dodeljivanje id korisniku
     * @param id Id koji treba dodeliti korisniku
     * @throws NullPointerException Ukoliko je prosledjeni id null metoda ce baciti ovaj exception
     * @throws IllegalArgumentException Ukoliko je id prazan bacice se ovaj exception
     */
    fun setId(id: String?){
        if (id==null)
            throw NullPointerException("ID ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("ID ne sme biti prazan")
        this.id=id
    }

    /**
     * Metoda za vracanje korisnickog email-a
     * @return Vraca se [email] korisnika
     */
    fun getEmail(): String{
        return this.email
    }

    /**
     * Metoda koja dodeljuje korisniku email
     *
     * @param email Email koji treba dodeliti korisniku
     * @throws NullPointerException Ukoliko je prosledjeni email null, bacice se izuzetak
     * @throws IllegalArgumentException Ukoliko je prosledjeni email prazan, bacice se izuzetak
     * @throws IllegalArgumentException Ukoliko prosledjeni email nije u formatu npr. nesto@nesto.com
     */
    fun setEmail(email: String?){
        if(email==null)
            throw NullPointerException("Email ne moze biti null")

        if(email.isEmpty()){
            throw IllegalArgumentException("Email ne moze biti prazan")
        }
        val regex = "^[\\w.-]+@[\\w.-]+\\.com$".toRegex()

        if(!regex.matches(email))
            throw IllegalArgumentException("Email mora biti u ispravnom formatu, na primer" +
                    "petar@gmail.com")
        this.email=email

    }

    /**
     * Metoda za vracanje lozinke korisnika
     *
     * @return [password] Vraca se lozinka u string formatu
     */
    fun getPassword():String{
        return this.password
    }

    /**
     * Metoda za dodelu lozinke User objektu
     *
     * @param password Lozinka koja se dodeljuje korisniku
     * @throws NullPointerException Ukoliko je lozinka null, baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je lozinka prazna ili nije u formatu:8 karaktera, minimun jedno veliko slovo, jedan broj i
     * jedan specijalan karaket baca se izuzetak
     */
    fun setPassword(password: String?){
        if(password==null)
            throw NullPointerException("Lozinka ne moze biti null")
        val regex = """^(?=.*[A-Z])(?=.*\d)(?=.*[@#\$%^&+=!]).{8,}$""".toRegex()
        if(password.isEmpty()||!regex.matches(password))
            throw IllegalArgumentException("Lozinka mora sadrzati bar jedno veliko slovo,jedan broj i jedan specijalan karakter")
        this.password=password

    }

    /**
     * Metoda koja vraca ulogu usera
     * @return Vraca se uloga usera(Lekar ili pacijent)
     * @see [Role]
     */
    fun getRole(): Role{
        return this.role
    }

    /**
     * Metoda za postavljanje uloge Usera
     * @param role Uloga korisnika(Lekar ili pacijent)
     * @throws NullPointerException Ukoliko je prosledjeni argument null baca se izuzetak
     * @see [Role]
     *
     */
    fun setRole(role: Role?){
        if(role==null)
            throw NullPointerException("Role ne moze biti null")
        this.role=role
    }

    /**
     * Metoda koja sluzi za poredjenje 2 [User] objekta
     * @param other Objekat bilo koje klase koji se poredi za user objektom
     * @return [Boolean] Ukoliko su objekti identicni vraca se true, ukoliko nisu iste klase false, i ukoliko im je ili id ili email
     * isti vraca se true, jer su oba treba da budu jedinstvena za svakog usera
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false


        other as User


        return id == other.id || email == other.email
    }

    /**
     * Metoda za generisanje hash koda za [User] objekta
     *
     * Hash kod se bazira na [id] i [email] kako bi bio konzistentan sa [equals] metodom
     *
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    /**
     * Vraca tekstualnu reprezentaciju [User] objekta
     * @return String u formatu "User(id='$id', email='$email', password='$password', role=$role)"
     *
     */
    override fun toString(): String {
        return "User(id='$id', email='$email', password='$password', role=$role)"
    }


}
