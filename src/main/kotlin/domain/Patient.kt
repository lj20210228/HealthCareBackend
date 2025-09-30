package com.example.domain

import kotlinx.serialization.Serializable

/**
 * Klasa koja sluzi za cuvanje podataka o pacijentima
 * @author Lazar Janković
 * @property id Id pacijenta
 * @property userId Spoljni kljuc ka [User] tabeli
 * @property fullName Ime i prezime pacijenta
 * @property hospitalId Id bolnice u kojoj se pacijent leci
 * @property jmbg Jedinstveni maticni broj pacijenta, sluzice najvise za pretragu
 * @see User
 */
@Serializable
data class Patient(
    private var id: String?=null,
    private var userId: String?=null,
    private var fullName: String,
    private var hospitalId: String,
    private var jmbg:String?
){
    companion object{
        /**
         * Regex koji sluzi za validaciju vrednosti atributa [fullName]
         */
        private val regex = Regex("^[a-zA-ZšđčćžŠĐČĆŽ\\s]*\$")
    }
    init {
        setFullName(fullName)
        setHospitalId(hospitalId)
        setJmbg(jmbg)
    }
    /**
     * Metoda koja vraca id pacijenta
     * @return String koji vraca [id] pacijenta
     */
    fun getId(): String?{
        return this.id
    }


    /**
     * Metoda za dodeljivanje id pacijentu
     * @param id Id koji treba dodeliti pacijentu
     * @throws NullPointerException Ukoliko je prosledjeni id null metoda ce baciti ovaj exception
     * @throws IllegalArgumentException Ukoliko je id prazan bacice se ovaj exception
     */
    fun setId(id: String?){
        if (id==null)
            throw NullPointerException("ID pacijenta ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("ID pacijenta ne sme biti prazan")
        this.id=id
    }

    /**
     * Metoda koja vraca id iz User tabele
     * @return [userId] UserId
     * @see [User]
     */
    fun getUserId(): String?{
        return this.userId
    }

    /**
     * Funkcija koja dodeljuje [userId] pacijentu
     * @param userId UserId koji se dodeljuje pacijentu
     * @throws NullPointerException Baca se izuzetak ako je null
     * @throws IllegalArgumentException Baca se izuzetak ako je prazan string
     * @see User
     */
    fun setUserId(userId: String?){
        if (userId==null)
            throw NullPointerException("UserId ne sme biti null")
        if(userId.isEmpty())
            throw IllegalArgumentException("UserId ne moze biti prazan string")
        this.userId=userId
    }
    /**
     * Vraca ime i prezime pacijenta
     * @return Ime i prezime kao [String]
     */
    fun getFullName(): String{
        return this.fullName
    }

    /**
     * Postavlja ime i prezime pacijenta
     *
     * @throws NullPointerException Ako je prosledjena null vrednost baca izuzetak
     * @throws IllegalArgumentException Ako prosledjeni string nema razmak
     * @throws IllegalArgumentException Ako prosledjeni string ima bilo sta sem slova
     *
     */
    fun setFullName(fullName: String?){
        if(fullName==null){
            throw NullPointerException("Ime i prezime ne smeju biti null")
        }
        if(!fullName.contains(" "))
            throw IllegalArgumentException("Morate uneti razmak izmedju imena i prezimena")
        if (!fullName.matches(regex)) {
            throw IllegalArgumentException("Ime i prezime mogu da sadrže samo slova.")
        }
        this.fullName=fullName
    }
    /**
     * Metoda koja vraca id bolnice u kojoj se pacijent leci
     * @return [hospitalId] String,id bolnice
     */
    fun getHospitalId(): String{
        return this.hospitalId
    }

    /**
     * Metoda koja postavlja id bolnice u kojoj se pacijent leci
     *
     * @throws NullPointerException Ako je prosledjena vrednost null
     * @throws IllegalArgumentException Ako je prosledjen prazan string
     * @see Hospital
     */
    fun setHospitalId(hospitalId: String?){
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne moze biti null")
        if (hospitalId.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti prazan")

        }
        this.hospitalId=hospitalId
    }

    /**
     * Funkcija koja vraca jmbg pacijenta
     */
    fun getJmbg(): String?{
        return this.jmbg
    }

    /**
     * Funkcija koja postavlja jmbg pacijenta
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan
     * @throws IllegalArgumentException Ako prosledjeni argument sadrzi manje od 13 cifara
     * @throws IllegalArgumentException Ako prosledjeni argument sadrzi bilo sta sem brojeva
     *
     * */
    fun setJmbg(jmbg:String?){
        if(jmbg==null){
            throw NullPointerException("Jmbg ne sme biti null")
        }
        if(jmbg.isEmpty())
            throw IllegalArgumentException("Jmbg ne sme biti prazan")

        if(!jmbg.all { it.isDigit() }){
            throw IllegalArgumentException("Jmbg mora sadrzati  samo brojeve")

        }
        if(jmbg.length!=13) {
            throw IllegalArgumentException("Jmbg mora imati tacno 13 cifara")

        }
        this.jmbg=jmbg
    }


    /**
     * Metoda koja proverava da li je neki objekat identican Doctor objektu
     *
     * @return [Boolean] Ukoliko se klasa razlikuje od [com.example.domain.Doctor] ili
     * ukoliko je [id] ili [userId]razlicit kod 2 objekta, u suportnom
     * vraca true
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Patient



        return id==other.id ||userId==other.userId  ||jmbg==other.jmbg  }
    /**
     * Generiše hash kod za [com.example.domain.Patient] objekat.
     *
     * Hash kod koristi ista polja kao [equals] kako bi se osigurala konzistentnost:
     * [id], [userId]
     *
     * @return Hash kod objekta
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result=31*result+jmbg.hashCode()
        return result
    }

    /**
     * Pretvara objekat [com.example.domain.Patient] u [String] niz
     * @return [String] Sa podacima o pacijentu
     */
    override fun toString(): String {
        return "Patient(id='$id', userId='$userId', fullName='$fullName', hospitalId='$hospitalId,jmbg='$jmbg')"
    }


}
