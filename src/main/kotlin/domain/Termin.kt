package com.example.domain

import com.example.date.StrictLocalDateSerializer
import com.example.date.StrictLocalTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

/**
 * Klasa za cuvanje podataka o terminima kod lekara
 * @author Lazar Janković
 * @property id Id termina
 * @property doctorId Id lekara kod koga se pregled zakazuje
 * @property patientId Id pacijenta koji zakazuje pregled
 * @property description Opis, na primer pregled rezultata, po default kontrola
 */
@Serializable
data class Termin(
    private var id: String?=null,
    private var doctorId: String,
    private var patientId: String,
    @Serializable(with = StrictLocalDateSerializer::class)
    private var date: LocalDate,
    @Serializable(with= StrictLocalTimeSerializer::class)
    private var startTime: LocalTime,
    @Serializable(with= StrictLocalTimeSerializer::class)
    private var endTime: LocalTime=startTime.plusHours(1),
    private var hospitalId: String,
    private var status: TerminStatus= TerminStatus.PENDING,
    private var description: String?="Kontrola",
){


    init {

        setDoctorId(doctorId)
        setPatientId(patientId)
        setHospitalId(hospitalId)
        setDate(date)
        setStartTime(startTime)
        setEndTime(endTime)
    }
    /**
     * Funkcija koja vraca id termina
     * @return [String] Id termina
     */
    fun getId(): String?{
        return this.id
    }
    /**
     * Funkcija koja postavlja id termina
     * @param id Id termina koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setId(id:String?){
        if(id==null)
            throw NullPointerException("Id termina ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id termina ne moze biti prazan")
        this.id=id
    }
    /**
     * Funkcija koja vraca id lekara
     * @return [String] Id lekara
     */
    fun getDoctorId(): String{
        return this.doctorId
    }
    /**
     * Funkcija koja postavlja id lekara
     * @param id Id termina koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setDoctorId(id:String?){
        if(id==null)
            throw NullPointerException("Id lekara ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id lekara ne moze biti prazan")
        this.doctorId=id
    }

    /**
     * Funkcija koja vraca id pacijenta
     * @return [String] Id termina
     */
    fun getPatientId(): String{
        return this.patientId
    }
    /**
     * Funkcija koja postavlja id pacijenta
     * @param id Id pacijenta koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setPatientId(id:String?){
        if(id==null)
            throw NullPointerException("Id pacijenta ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne moze biti prazan")
        this.patientId=id
    }
    /**
     * Funckija koja vraca id bolnice
     * @return [id] String koji sadrzi id bolnice
     */
    fun getHospitalId(): String{
        return this.hospitalId
    }

    /**
     * Funkcija koja postavlja id bolnice
     * @param id Id bolnice koji treba postaviti
     * @throws NullPointerException Ukoliko je prosledjena null vrednost baca se izuzetak
     * @throws IllegalArgumentException Ukoliko se prosledi prazan string baca se izuzetak
     */
    fun setHospitalId(id: String?){
        if(id==null){
            throw NullPointerException("Id bolnice ne sme biti null")

        }
        if(id.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti prazan string")
        }
        this.hospitalId=id
    }
    /**
     * Funkcija koja vraca datum termina
     * @return [LocalDate] Datum termina
     */
    fun getDate(): LocalDate{
        return this.date
    }

    /**
     * Funkcija koja postavlja datum pregleda
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni datum pre danasnjeg
     * @param date Datum koji treba postaviti
     */
    fun setDate(date: LocalDate?){
        if (date==null)
            throw NullPointerException("Datum termina ne moze biti null")
        if (date.isBefore(LocalDate.now()))
            throw IllegalArgumentException("Datum pocetka ne moze biti pre danasnjeg")
        this.date=date
    }

    /**
     * Funkcija koja vraca vreme pocetka pregleda
     * @return [LocalTime] Vreme pocetka pregleda
     */
    fun getStartTime(): LocalTime{
        return this.startTime
    }

    /**
     * Funkcija koja postavlja datum pocetka pregleda
     * @param startTime Datum pocetka pregleda koji treba postaviti
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument posle [endTime]
     */
    fun setStartTime(startTime: LocalTime?){
        if(startTime==null)
            throw NullPointerException("Pocetak termina ne moze biti null")
        if (startTime.isAfter(endTime))
            throw IllegalArgumentException("Pocetak termina ne moze biti posle kraja")
        this.startTime=startTime
    }
    /**
     * Funkcija koja vraca vreme kraja pregleda
     * @return [LocalTime] Vreme kraja pregleda
     */
    fun getEndTime(): LocalTime{
        return this.endTime
    }

    /**
     * Funkcija koja postavlja datum kraja pregleda
     * @param endTime Datum kraja pregleda koji treba postaviti
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument pre [startTime]
     */
    fun setEndTime(endTime: LocalTime?){
        if(endTime==null)
            throw NullPointerException("Kraj termina ne moze biti null")
        if (endTime.isBefore(startTime))
            throw IllegalArgumentException("Kraj termina ne moze biti pre poetka")
        this.endTime=endTime
    }

    /**
     * Funkcija koja vraca podatke o statusu termina
     * @return [TerminStatus] Status termina
     */
    fun getTerminStatus(): TerminStatus{
        return this.status
    }

    /**
     * Funkcija koja postavlja vrednost statusa termina
     * @param status Status koji treba da se postavi
     * @throws NullPointerException Ukoliko je prosledjeni argument null
     */
    fun setTerminStatus(status: TerminStatus?){
        if (status==null)
            throw NullPointerException("Status ne moze biti null")
        this.status=status
    }
    /**
     * Funkcija za vracanje opisa
     * @return String
     */
    fun getDescription(): String?{
        return this.description
    }

    /**
     * Funkcija za postavljanje opisa termina
     * @param description opis pregleda koji treba postaviti
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan
     */
    fun setDescription(description: String?){
        if(description==null)
            throw NullPointerException("Opis ne moze biti null")
        if (description.isEmpty())
            throw IllegalArgumentException("Opis ne moze biti pre poetka")
        this.description=description
    }


    /**
     * Metoda koja pretvara objekat klase [com.example.domain.Termin]
     * u string sa podacima o njemu
     * @return [String] sa podacima o objektu
     */
    override fun toString(): String {
        return "Termin(id='$id', doctorId='$doctorId', patientId='$patientId', date=$date, startTime=$startTime, endTime=$endTime, hospitalId='$hospitalId')"
    }
    /**
     * Metoda koja poredi 2 objekta [com.example.domain.Termin]
     * @param other Objekat koji se treba uporediti sa objektom klase [com.example.domain.Termin]
     * @return [Boolean] Ukoliko su 2 objekta razlicitog tipa vraca se false, ili ukoliko im je bar jedan parametar razlicit,
     * a ukoliko su im svi parametni jednaki vraca se true
     */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Termin

        if (id != other.id) return false
        if (doctorId != other.doctorId) return false
        if (patientId != other.patientId) return false
        if (date != other.date) return false
        if (startTime != other.startTime) return false
        if (endTime != other.endTime) return false
        if (hospitalId != other.hospitalId) return false
        if (status != other.status) return false

        return true
    }
    /**
     * Metoda koja hesuje objekay i vraca int vrednost
     * @return [Int] Hash vrednost objekta
     */
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + doctorId.hashCode()
        result = 31 * result + patientId.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + hospitalId.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }






}
