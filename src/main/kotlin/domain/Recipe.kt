package com.example.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate


@Serializable
data class Recipe(
    private var id: String,
    private var patientId: String,
    private var doctorId:String,
    private var medication: String,
    private var quantity: Int,
    private var instructions:String?=null,
    @Serializable(with = StrictLocalDateSerializer::class)
    private var dateExpired: LocalDate
){
    companion object{
        private val regex = Regex("^[a-zA-ZšđčćžŠĐČĆŽ\\s]*\$")

    }
    fun getId(): String{
        return this.id
    }
    fun setId(id: String?){
        if (id==null)
            throw NullPointerException("Id recepta ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id recepta ne sme biti prazan")
        this.id=id
    }
    fun getPatientId():String{
        return this.patientId
    }
    fun setPatientId(patientId:String?){
        if (patientId==null)
            throw NullPointerException("Id pacijenta kome je prepisan recept ne moze biti null")
        if(patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta kome je prepisan recept ne moze biti null")
        this.patientId=patientId
    }
    fun getDoctorId():String{
        return this.doctorId
    }
    fun setDoctorId(doctorId:String?){
        if (doctorId==null)
            throw NullPointerException("Id lekara koji je prepisao recept ne moze biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara koji je prepisao recept ne moze biti null")
        this.doctorId=doctorId
    }
    fun getMedication():String{
        return this.medication
    }
    fun setMedication(medication: String?){
        if (medication==null)
            throw NullPointerException("Ime leka ne moze biti null")
        if(medication.isEmpty())
            throw IllegalArgumentException("Ime leka ne moze biti prazan string")
        if (!medication.matches(regex))
            throw IllegalArgumentException("Ime leka moze sadrzati samo slova")
        this.medication=medication
    }
    fun getQuantity():Int{
        return this.quantity
    }
    fun setQuantity(quantity: Int?){
        if (quantity==null)
            throw NullPointerException("Kolicina ne sme biti null")
        if(quantity<1){
            throw IllegalArgumentException("Minimalna kolicina je jedna kutija/tableta")
        }
        this.quantity=quantity
    }
    fun getInstructions(): String?{
        return this.instructions
    }
    fun setInstructions(instructions: String?){
        this.instructions=instructions
    }
    fun getExpiredDate(): LocalDate{
        return this.dateExpired
    }
    fun setDateExpired(dateExpired: LocalDate?){
        if (dateExpired==null)
            throw NullPointerException("Datum isteka recepta ne moze biti null")
        if (dateExpired.isBefore(LocalDate.now()))
            throw IllegalArgumentException("Datum isteka ne moze biti u proslosti")

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (quantity != other.quantity) return false
        if (id != other.id) return false
        if (patientId != other.patientId) return false
        if (doctorId != other.doctorId) return false
        if (medication != other.medication) return false
        if (instructions != other.instructions) return false
        if (dateExpired != other.dateExpired) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quantity
        result = 31 * result + id.hashCode()
        result = 31 * result + patientId.hashCode()
        result = 31 * result + doctorId.hashCode()
        result = 31 * result + medication.hashCode()
        result = 31 * result + (instructions?.hashCode() ?: 0)
        result = 31 * result + dateExpired.hashCode()
        return result
    }

    override fun toString(): String {
        return "Recipe(id='$id', patientId='$patientId', doctorId='$doctorId', medication='$medication', quantity=$quantity, instructions=$instructions, dateExpired=$dateExpired)"
    }

}
