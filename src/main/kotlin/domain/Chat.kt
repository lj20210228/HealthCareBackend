package com.example.domain

import kotlinx.serialization.Serializable

/**
 * Domenska klasa koja sluzi za rukovanje podataka o cetovima izmedju lekara i pacijenata
 * @param id Id ceta
 * @param doctorId Id lekara
 * @param patientId Id pacijenta
 */
@Serializable
data class Chat(
    private var id: String?=null
    ,private var doctorId: String,
    private var patientId: String
){

    /**
     * Funkcija koja vraca id [com.example.domain.Chat]
     * @return [String?] Id chata ili null(nece vratiti null jer se automatski generise u bazi, ali mora
     * povratni tip da bude nullable zbog kotlin sintakse)
     */
    fun getId(): String?{
        return this.id
    }

    /**
     * Funkcija za postavljanje id ceta
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjena vrednost prazan string
     */
    fun setId(id: String?){
        if (id==null)
            throw NullPointerException("Id ceta ne moze biti null")
        if (id.isEmpty())
            throw IllegalArgumentException("Id ceta ne moze biti prazan")
        this.id=id
    }
    /**
     * Funkcija koja vraca id pacijenta[com.example.domain.Chat]
     * @return [String?] Id pacijenta u cetu
     */
    fun getPatientId(): String{
        return this.patientId

    }
    /**
     * Funkcija za postavljanje id pacijenta u cetu
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjena vrednost prazan string
     */
    fun setPatientId(patientId: String?){
        if (patientId==null)
            throw NullPointerException("Id pacijenta u cetu ne moze biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta u cetu ne moze biti prazan")
        this.patientId=patientId
    }
    /**
     * Funkcija koja vraca id lekara[com.example.domain.Chat]
     * @return [String?] Id lekara u cetu
     */
    fun getDoctorId(): String{
        return this.doctorId

    }
    /**
     * Funkcija za postavljanje id lekara u cetu
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjena vrednost prazan string
     */
    fun setDoctorId(doctorId: String?){
        if (doctorId==null)
            throw NullPointerException("Id lekara u cetu ne moze biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara u cetu ne moze biti prazan")
        this.doctorId=doctorId
    }

    /**
     * Funckija koja poredi 2 [com.example.domain.Chat] objekta
     * @return Ukoliko su razlicite klase vraca false ili ako im je barem jedan atribut razlicit,
     * ukoliko su svi atributi jednaki vraca true
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chat



        return (id==other.id)||(doctorId==other.doctorId&&other.patientId==patientId)
    }

    /**
     * Funkcija koja sluzi za hesovanje [Chat] objekata,
     * @return [Int] Hesovana vrednost [Chat] objekta
     */
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + doctorId.hashCode()
        result = 31 * result + patientId.hashCode()
        return result
    }

    /**
     * Funkcija koja pretvara objekat u niz karaktera
     * @return [String] Vracaju se podaci o objektu kao recenica
     */
    override fun toString(): String {
        return "Chat(id=$id, doctorId='$doctorId', patientId='$patientId')"
    }


}
