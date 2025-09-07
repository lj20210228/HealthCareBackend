package com.example.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate


/**
 * Klasa koja predtavlja recept koji lekar izdaje pacijentu
 *
 *
 * @author Lazar Janković
 * @property id Id recepta koji sluzi kao identifikator recepta
 * @property patientId Id pacijenta kome je lek prepisan
 * @property doctorId Id lekara koji je prepisao recept
 * @property medication Ime leka koji je prepisan
 * @property quantity Kolicina leka koji je prepisan u tabletama ili kutijama, ukoliko nije navedeno u [instructions] drugacije
 * @property instructions Uputstvo za upotrebu leka, kao i napomena u vezi kolicine
 * @property dateExpired [LocalDate] datum kada recept istice u formatu yyyy-MM-dd
 */
@Serializable
data class Recipe(
    private var id: String,
    private var patientId: String,
    private var doctorId:String,
    private var medication: String,
    private var quantity: Int,
    private var instructions:String,
    @Serializable(with = StrictLocalDateSerializer::class)
    private var dateExpired: LocalDate
){

    /**
     * Blok za inicijalizaciju klase [com.example.domain.Recipe]
     */
    init {
        setId(id)
        setPatientId(patientId)
        setDoctorId(doctorId)
        setMedication(medication)
        setQuantity(quantity)
        setInstructions(instructions)
        setDateExpired(dateExpired)

    }
    /**
     * Metoda koja vraca id recepta
     * @return id recepta
     */
    fun getId(): String{
        return this.id
    }

    /**
     * Metoda koja postavja id recepta
     * @param id  Id recepta
     * @throws NullPointerException Ako je id null metoda baca izuzetak
     * @throws IllegalArgumentException Ako je id prazan string metoda baza izuzetak
     */
    fun setId(id: String?){
        if (id==null)
            throw NullPointerException("Id recepta ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id recepta ne sme biti prazan")
        this.id=id
    }
    /**
     * Metoda koja vraca id pacijenta
     * @return id pacijenta
     */
    fun getPatientId():String{
        return this.patientId
    }
    /**
     * Metoda koja postavja id pacijenta
     * @param id  Id pacijenta
     * @throws NullPointerException Ako je [patientId] null metoda baca izuzetak
     * @throws IllegalArgumentException Ako je [patientId] prazan string metoda baza izuzetak
     */
    fun setPatientId(patientId:String?){
        if (patientId==null)
            throw NullPointerException("Id pacijenta kome je prepisan recept ne moze biti null")
        if(patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta kome je prepisan recept ne moze biti null")
        this.patientId=patientId
    }
    /**
     * Metoda koja vraca id lekara
     * @return id lekara
     */
    fun getDoctorId():String{
        return this.doctorId
    }
    /**
     * Metoda koja postavja id lekara
     * @param id  Id lekara
     * @throws NullPointerException Ako je [doctorId] null metoda baca izuzetak
     * @throws IllegalArgumentException Ako je [doctorId] prazan string metoda baza izuzetak
     */
    fun setDoctorId(doctorId:String?){
        if (doctorId==null)
            throw NullPointerException("Id lekara koji je prepisao recept ne moze biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara koji je prepisao recept ne moze biti null")
        this.doctorId=doctorId
    }

    /**
     * Metoda koja vraca ime leka iz recepta
     * @return Ime leka
     */
    fun getMedication():String{
        return this.medication
    }

    /**
     * Metoda koja postavlja ime leka u receptu
     * @param medication Ime leka
     * @throws NullPointerException Ukoliko je prosledjeni parametar null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je prosledjeni parametar prazan string baca se izuzetak
     */
    fun setMedication(medication: String?){
        if (medication==null)
            throw NullPointerException("Ime leka ne moze biti null")
        if(medication.isEmpty())
            throw IllegalArgumentException("Ime leka ne moze biti prazan string")

        this.medication=medication
    }

    /**
     * Metoda koja vraca kolicinu leka koji je prepisan u receptu
     * @return Kolicina leka koji je prepisan
     */
    fun getQuantity():Int{
        return this.quantity
    }

    /**
     * Metoda koja postavlja kolicinu leka koji je prepisan
     * @param quantity Kolicina leka koji je prepisan, broj kutija
     * @throws NullPointerException Ako je parametar null baca se izuzetak
     * @throws IllegalArgumentException Ako je parametar manji  od 1 baca se izuzetak
     */
    fun setQuantity(quantity: Int?){
        if (quantity==null)
            throw NullPointerException("Kolicina ne sme biti null")
        if(quantity<1){
            throw IllegalArgumentException("Minimalna kolicina je jedna kutija/tableta")
        }
        this.quantity=quantity
    }

    /**
     * Metoda koja vraca instrukcije dodeljene za neki lek, to jest kako se koristi
     * @return Instrukcije za odredjeni lek
     */
    fun getInstructions(): String?{
        return this.instructions
    }

    /**
     * Metoda koja postavlja instrukcije za koriscenje leka, kako se pije, koliko dugo...
     * @param instructions Instrukcije za pijenje leka
     * @throws NullPointerException Prosledjeni parametar ne moze biti null
     * @throws IllegalArgumentException Prosledjeni parametar ne moze biti prazan string
     *
     */
    fun setInstructions(instructions: String?){
        if (instructions==null)
            throw NullPointerException("Instrukcije ne mogu biti null")
        if(instructions.isEmpty()){
            throw IllegalArgumentException("Instrukcije ne smeju biti prazan string")
        }

        this.instructions=instructions
    }

    /**
     * Metoda koja vraca datum isteka recepta
     * @return [LocalDate] Datum isteka recepta
     */
    fun getExpiredDate(): LocalDate{
        return this.dateExpired
    }

    /**
     * Metoda koja postavlja datum isteka recepta
     * @param dateExpired [LocalDate] vrednost koja predstavlja datum isteka recepta
     * @throws NullPointerException Ukoliko je prosledjena vrednost null
     * @throws IllegalArgumentException Ukoliko je prosledjeni datum u proslosti
     */
    fun setDateExpired(dateExpired: LocalDate?){
        if (dateExpired==null)
            throw NullPointerException("Datum isteka recepta ne moze biti null")
        if (dateExpired.isBefore(LocalDate.now()))
            throw IllegalArgumentException("Datum isteka ne moze biti u proslosti")
        this.dateExpired=dateExpired

    }

    /**
     * Metoda koja poredi 2 objekta klase [com.example.domain.Recipe].
     *
     * Pravila poređenja:
     * - Ako je `id` isti → objekti se smatraju jednakim bez obzira na ostale atribute.
     * - Ako je `id` različit → objekti se smatraju jednakim samo ako su svi ostali atributi jednaki:
     *   `quantity`, `patientId`, `doctorId`, `medication`, `instructions`, `dateExpired`.
     *
     * @return [Boolean] true ako su objekti jednaki prema definisanim pravilima, false inače
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id == other.id) return true

        return quantity == other.quantity &&
                patientId == other.patientId &&
                doctorId == other.doctorId &&
                medication == other.medication &&
                instructions == other.instructions &&
                dateExpired == other.dateExpired
    }

    /**
     * Metoda koja pretvara objekat klase [com.example.domain.Recipe] u hashCode.
     *
     * Pravila moraju biti u skladu sa equals:
     * - Ako se dva objekta porede po `id`, njihov hashCode se računa na osnovu `id`.
     * - Ako se porede po ostalim atributima, hashCode se računa na osnovu njih.
     *
     * @return [Int] hashCode objekta [com.example.domain.Recipe]
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + quantity.hashCode()
        result = 31 * result + patientId.hashCode()
        result = 31 * result + doctorId.hashCode()
        result = 31 * result + medication.hashCode()
        result = 31 * result + (instructions.hashCode())
        result = 31 * result + dateExpired.hashCode()
        return result
    }

    /**
     * Metoda koja pretvara objekat [com.example.domain.Recipe] u string objekat gde su opisani njegovi atributi
     * @return [String] Koji sadrzi podatke o objektu
     */
    override fun toString(): String {
        return "Recipe(id='$id', patientId='$patientId', doctorId='$doctorId', medication='$medication', quantity=$quantity, instructions=$instructions, dateExpired=$dateExpired)"
    }

}
