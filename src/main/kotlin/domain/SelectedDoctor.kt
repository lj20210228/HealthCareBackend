package com.example.domain

import kotlinx.serialization.Serializable

/**
 * Klasa koja cuva podatke o izabranim lekarima za pacijente, jer postoji vise izabranih lekara koje pacijent moze da ima,
 * iz tog razloga se podaci o izabranom lekaru ne cuvaju u klasi [Patient], sluzi kao veza izmedju tabela [Patient] i [Doctor]
 * @property patientId Id pacijenta kome je lekar izabran
 * @property doctorId Id lekara koji je izabrani lekar pacijentu
 * @see Doctor
 * @see Patient
 */
@Serializable
data class SelectedDoctor(
    private var patientId: String,
    private var doctorId: String
){

    /**
     * Funkcija koja vraca id pacijenta
     * @return [patientId] Id pacijenta
     */
    fun getPatientId(): String{
        return this.patientId
    }

    /**
     * Funkcija koja dodeljuje [patientId] kome treba dodeliti i izabranog lekara
     * @param patientId Id pacijenta
     */
    fun setPatientId(patientId: String?){
        if(patientId==null)
            throw NullPointerException("Id pacijenta kome se dodeljuje lekar ne moze biti null")
        if(patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta kome se dodeljuje lekar ne moze biti prazan string")
        this.patientId=patientId
    }
    /**
     * Funkcija koja vraca id lekara
     * @return [doctorId] Id lekara
     */
    fun getDoctorId(): String{
        return this.doctorId
    }
    /**
     * Funkcija koja dodeljuje [doctorId] koji ce pacijentu biti izabrani lekar
     * @param patientId Id lekara koji ce pacijentu biti izabran
     */
    fun setDoctorId(doctorId: String?){
        if (doctorId==null)
            throw NullPointerException("Id lekara kome se dodeljuje pacijent ne moze biti null")
        if(doctorId.isEmpty()){
            throw IllegalArgumentException("Id lekara kome se dodeljuje pacijent ne moze biti prazan string")
        }
        this.doctorId=doctorId
    }

    /**
     * Metoda koja poredi 2 [com.example.domain.SelectedDoctor] objekta
     * @param other Objekat bilo koje klase koji ce da se poredi sa datim
     * @return [Boolean] Vraca se vrednost false ako objekat nije [com.example.domain.SelectedDoctor] ili ako je bar jedan parametar razlicit,
     * ili true ako se ispostavi da su oba argumenta identicna
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectedDoctor

        if (patientId != other.patientId) return false
        if (doctorId != other.doctorId) return false

        return true
    }

    /**
     * Metoda koja hesuje objekat klase [com.example.domain.SelectedDoctor]
     * @return [Int] vrednost koja predstavlja hash vrednost objekta
     */
    override fun hashCode(): Int {
        var result = patientId.hashCode()
        result = 31 * result + doctorId.hashCode()
        return result
    }

    /**
     * Metoda koja pretvara objekat klase [com.example.domain.SelectedDoctor] u string niz
     * @return vraca se string niz sa podacima o objektu
     */
    override fun toString(): String {
        return "SelectedDoctor(patientId='$patientId', doctorId='$doctorId')"
    }

}
