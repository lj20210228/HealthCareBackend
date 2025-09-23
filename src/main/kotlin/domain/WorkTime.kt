package com.example.domain

import io.ktor.util.reflect.instanceOf
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalTime
import kotlin.reflect.KClass

/**
 *Klasa koja sluzi za podatke o radnom vremenu lekara, cuva podatke za svaki dan posebno
 * @author Lazar Janković
 * @property id Id radnog vremena
 * @property startTime Pocetak radnog vremena, ako je null dan je neradan
 * @property endTime Kraj radnog vremena, ako je null dan je neradan
 * @property doctorId Id lekara, koji ima odredjeno radno vreme
 * @property dayIn Dan u nedelji na koji se odnosi radno vreme
 */
@Serializable
data class WorkTime(
    private var id: String,
    @Contextual
    private var startTime: LocalTime?=null,
    @Contextual
    private var endTime: LocalTime?=null,
    private var doctorId: String,
    private var dayIn: DayInWeek

){
    /**
     * Blok za inicijalizaciju
     */
    init {

        setId(id)

        setDoctorId(doctorId)
        setDayIn(dayIn)
    }

    /**
     * Funkcija koja vraca id radnog vremena
     * @return [String] id  radnog vremena
     */
    fun getId(): String{
        return this.id
    }
    /**
     * Funkcija koja postavlja id radnog vremena
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjena vrednost prazna
     */
    fun setId(id: String?){
        if(id==null)
            throw NullPointerException("Id ne sme biti null")
        if(id.isEmpty()){
            throw NullPointerException("Id ne sme biti prazan string")
        }
        this.id=id
    }
    /**
     * Funkcija koja vraca id lekara
     * @return [String] id  lekara
     */

    fun getDoctorId(): String{
        return this.doctorId
    }
    /**
     * Funkcija koja postavlja id lekara
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjena vrednost prazna
     */
    fun setDoctorId(doctorId: String?){
        if(doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(doctorId.isEmpty()){
            throw NullPointerException("Id lekara sme biti prazan string")
        }
        this.doctorId=doctorId
    }

    /**
     * Funkcija koja vraca pocetak radnog vremena
     * @return [LocalTime] vraca vreme pocetka smene
     */
    fun getStartTime(): LocalTime?{
        return this.startTime
    }

    /**
     * Funkcija koja postavlja pocetak radnog vremena
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @throws IllegalArgumentException Ukoliko je vec postavljen kraj radnog vremena i pocetak je posled njega
     */
    fun setStartTime(startTime: LocalTime?){
        val notNullStart=requireNotNull(startTime){"Pocetak radnog vremena ne moze biti null"}
        if (endTime != null && notNullStart.isAfter(endTime)) {
            throw IllegalArgumentException("Pocetak radnog vremena ne moze biti posle kraja radnog vremena")
        }
        this.startTime = notNullStart
    }
    /**
     * Funkcija koja vraca kraj radnog vremena
     * @return [LocalTime] vraca vreme kraja smene
     */
    fun getEndTime(): LocalTime?{
        return this.endTime
    }
    /**
     * Funkcija koja postavlja kraj radnog vremena
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     * @throws IllegalArgumentException Ukoliko je vec postavljen pocetak radnog vremena i kraj je pre njega
     */
    fun setEndTime(endTime: LocalTime?){
        val notNullEnd=requireNotNull(endTime){"Kraj radnog vremena ne moze biti null"}
        if (startTime != null && notNullEnd.isBefore(startTime)) {
            throw IllegalArgumentException("Kraj radnog vremena ne moze biti pre pocetka radnog vremena")
        }
        this.endTime = notNullEnd
    }

    /**
     * Funckija koja vraca dan u nedelji na koga se radno vreme odnosi
     * @return [DayInWeek] Vraca se dan na koji se radno vreme odnosi
     */
    fun getDayIn(): DayInWeek{
        return this.dayIn
    }
    /**
     * Funckija koja postavlja dan u nedelji na koga se radno vreme odnosi
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     */
    fun setDayIn(dayInWeek: DayInWeek?){
        if(dayInWeek==null){
            throw NullPointerException("Dan ne moze biti null")
        }
        this.dayIn=dayInWeek
    }
}
