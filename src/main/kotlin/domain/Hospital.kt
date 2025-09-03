package com.example.domain

import kotlinx.serialization.Serializable

/**
 * Klasa koja sluzi za cuvanje podataka o bolnici
 *
 *
 * @author Lazar Janković
 * @property id String koji sluzi kao identifikator za svaki objekat
 * @property name Ime bolnice
 * @property city Grad u kome se nalazi
 * @property address Adresa na kojoj se bolnica nalazi
 */
@Serializable
data class Hospital(
    private var id: String,
    private var name: String,
    private var city: String,
    private var address: String
){
    companion object{
        /**
         * Regex koji se koristi za validaciju imena grada bolnice
         */
        private val regex = Regex("^[a-zA-ZšđčćžŠĐČĆŽ\\s]*\$")

        /**
         * Regex koji sluzi za validaciju imena adrese bolnice
         */
        private val regex2 = Regex("^[a-zA-ZšđčćžŠĐČĆŽ0-9\\s]*\$")

    }

    /**
     * Funckija koja vraca id bolnice
     * @return [id] String koji sadrzi id bolnice
     */
    fun getId(): String{
        return this.id
    }

    /**
     * Funkcija koja postavlja id bolnice
     * @param id Id bolnice koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setId(id: String?){
        if(id==null){
            throw NullPointerException("Id bolnice ne sme biti null")

        }
        if(id.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti prazan string")
        }
        this.id=id
    }
    /**
     * Funckija koja vraca ime bolnice
     * @return [name] String koji sadrzi ime bolnice
     */
    fun getName(): String{
        return this.name
    }
    /**
     * Funkcija koja postavlja ime bolnice, nije dodat uslov za proveru da li sadrzi brojeve i specijalne znakove,
     * zbog toga sto postoje bolnice npr "Narodni front" koji ima specijalne znakove
     * @param name Ime bolnice koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setName(name: String?){
        if(name==null){
            throw NullPointerException("Ime bolnice ne moze biti null")
        }
        if(name.isEmpty()){
            throw IllegalArgumentException("Ime bolnice ne moze biti prazno")
        }


        this.name=name
    }
    /**
     * Funckija koja vraca grad bolnice
     * @return [city] String koji sadrzi grad bolnice
     */
    fun getCity(): String{
        return this.city

    }
    /**
     * Funkcija koja postavlja grad bolnice
     * @param name Grad bolnice koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi  string koji nema samo slova
     *
     */
    fun setCity(city: String?){
        if(city==null)
            throw NullPointerException("Ime grada ne moze biti null")
        if(city.isEmpty())
            throw IllegalArgumentException("Ime grada ne moze biti prazan string")
        if(!city.matches(regex))
            throw IllegalArgumentException("Ime grada moze sadrzati samo slova")
        this.city=city
    }
    /**
     * Funckija koja vraca adresu bolnice
     * @return [address] String koji sadrzi adresu bolnice
     */
    fun getAddress(): String{
        return this.address
    }
    /**
     * Funkcija koja postavlja adresu bolnice
     * @param name Adresa bolnice koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi  string koji nema samo slova i brojeve baca se izuzetak
     *
     */
    fun setAddress(address: String?){
        if (address==null)
            throw NullPointerException("Adresa bolnice ne moze biti null")
        if (address.isEmpty())
            throw IllegalArgumentException("Adresa ne moze biti prazan string")
        if(!address.matches(regex2))
            throw IllegalArgumentException("Adresa moze sadrzati samo slova i brojeve")
        this.address=address
    }

    /**
     * Metoda koja poredi 2 objekta, jedan [com.example.domain.Hospital] i jedan Any
     * @return Ukoliko objekat nije [Hospital] vraca true,
     * ukoliko im je neki parametar drugaciji vraca se false
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hospital

        if (id != other.id) return false
        if (name != other.name) return false
        if (city != other.city) return false
        if (address != other.address) return false

        return true
    }

    /**
     * Funkcija koj pravi hashCode od [com.example.domain.Hospital] objekta
     * @return [Int] vrednost koja predstavlja hash objekta
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + address.hashCode()
        result=31*result+id.hashCode()
        return result
    }

    /**
     * Funkcija koja pretvara objekat [com.example.domain.Hospital] u string
     * @return [String] Hospital objekat u [String] verziji, gde su navedeni svi njegovi atributi
     */
    override fun toString(): String {
        return "Hospital(id='$id', name='$name', city='$city', address='$address')"
    }


}