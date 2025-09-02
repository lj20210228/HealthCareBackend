package com.example.domain

import kotlinx.serialization.Serializable

/**
 * Klasa koja sluzi za cuvanje podataka o lekarima
 *
 * @author Lazar Janković
 * @property id Id lekara
 * @property userId Id iz tabele user, sluzi kao spoljni kljuc,
 * @property fullName Ime i prezime lekara
 * @property specialization Specijalizcija lekara
 * @property maxPatients Maksimalan broj pacijenata kojima jedan lekar moze biti izabrani
 * @property currentPatients Trenutan broj pacijenata kojima je lekar izabrani
 * @property hospitalId Id bolnice u kojoj lekar radi
 * @property isGeneral Da li je lekar opste prakse
 * @property regex Sluzi za proveru da li se u stringu nalaze samo slova
 * @see Hospital
 * @see User
 *
 */


@Serializable
data class Doctor(
    private var id: String,
    private var userId: String,
    private var fullName:String,
    private var specialization: String,
    private var maxPatients:Int,
    private var currentPatients:Int=0,
    private  var hospitalId: String,
    private var isGeneral: Boolean=false
){


    companion object{
        /**
         * Regex koji se koristi za validaciju imena, prezimena i specijalizacije.
         * Dozvoljena su samo slova (uključujući šđčćž) i razmaci.
         */
        private val regex = Regex("^[a-zA-ZšđčćžŠĐČĆŽ\\s]*\$")

    }
    /**
     * Metoda koja vraca id lekara
     * @return String koji vraca [id] lekara
     */
    fun getId(): String{
        return this.id
    }


    /**
     * Metoda za dodeljivanje id lekaru
     * @param id Id koji treba dodeliti lekaru
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
     * Metoda koja vraca id iz User tabele
     * @return [userId] UserId
     * @see [User]
     */
    fun getUserId(): String{
        return this.userId
    }

    /**
     * Funkcija koja dodeljuje [userId] lekaru
     * @param userId UserId koji se dodeljuje lekaru
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
     * Vraca ime i prezime lekara
     * @return Ime i prezime kao [String]
     */
    fun getFullName(): String{
        return this.fullName
    }

    /**
     * Postavlja ime i prezime lekara
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
     * Funkcija koja vraca naziv specijalizacije lekara
     * @return [specialization] Naziv specijalizacije
     */
    fun getSpecialization(): String{
        return this.specialization
    }

    /**
     * Funkcija koja postalja naziv specijalizacije lekara
     *
     * @throws NullPointerException Ako je prosledjena vrednost null baca se izuzetak
     * @throws IllegalArgumentException Ako je prosledjeni string prazan
     * @throws IllegalArgumentException Ako prosledjeni string ima bilo sta sem slova
     */
    fun setSpecialization(specialization: String?)
    {
        if(specialization==null)
            throw NullPointerException("Specijalizacija ne sme biti null")
        if(!specialization.matches(regex)){
            throw IllegalArgumentException("Specijalizaija moze da sadrzi samo slova")

        }
        if(specialization.isEmpty()){
            throw IllegalArgumentException("Specijalizaija ne moze biti prazna")

        }
        this.specialization=specialization

    }

    /**
     * Metoda koja vraca maksimalni broj pacijenata kojima lekar moze biti izabrani
     * @return [maxPatients] Int vrednost,maksimalni broj pacijenata
     */
    fun getMaxPatients(): Int{
        return this.maxPatients
    }

    /**
     * Metoda koja postavlja maksimalni broj pacijenata lekaru
     *
     * @throws IllegalArgumentException Ako je prosledjeni broj nula
     * @throws NullPointerException Ako je prosledjena null vrednost
     */
    fun setMaxPatients(max:Int?){
        if (max==0)
            throw IllegalArgumentException("Maksimalan broj pacijenata mora biti veci od 0")
        if(max==null){
            throw NullPointerException("Maksimalan broj pacijenata mora da ima vrednost")

        }

        this.maxPatients=maxPatients
    }
    /**
     * Metoda koja vraca trenutni broj pacijenata kojima lekar moze biti izabrani
     * @return [currentPatients] Int vrednost,maksimalni broj pacijenata
     */
    fun getCurrentPatients():Int{
        return this.currentPatients
    }
    /**
     * Metoda koja postavlja maksimalni broj pacijenata lekaru
     *
     * @throws NullPointerException Ako je prosledjena null vrednost
     */

    fun setCurrentPatients(patients:Int?){
        if (patients==null)
            throw NullPointerException("Broj pacijenata mora da ima vrednost")
        this.currentPatients=currentPatients


    }

    /**
     * Metoda koja vraca id bolnice u kojoj lekar radi
     * @return [hospitalId] String,id bolnice
     */
    fun getHospitalId(): String{
        return this.hospitalId
    }

    /**
     * Metoda koja postavlja id bolnice u kojoj lekar radi
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
     * Metoda koja proverava da li je lekar opste prakse
     * @return Ako jeste vraca true, u suprotnom false
     */
    fun getIsGeneral(): Boolean{
        return this.isGeneral
    }

    /**
     * Metoda koja odredjuje da li je lekar opste prakse
     * @param isGeneral Ako jeste salje se true, ako ne false
     * @throws NullPointerException Ako je prosledjen null baca se izuzetak
     */
    fun setIsGeneral(isGeneral: Boolean?){
        if (isGeneral==null){
            throw NullPointerException("IsGeneral mora imati vrednost")
        }
        this.isGeneral=isGeneral
    }

    /**
     * Pretvara objekat u [String] niz
     * @return [String] Sa podacima o lekaru
     */
    override fun toString(): String {
        return "Doctor(id='$id', userId='$userId', fullName='$fullName', specialization='$specialization', maxPatients=$maxPatients, currentPatients=$currentPatients, hospitalId='$hospitalId', isGeneral=$isGeneral)"
    }

    /**
     * Metoda koja proverava da li je neki objekat identican Doctor objektu
     *
     * @return [Boolean] Ukoliko se klasa razlikuje od [com.example.domain.Doctor] ili
     * ukoliko je [id],[userId],[fullName] ili [specialization] razlicit kod 2 objekta, u suportnom
     * vraca true
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Doctor

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (fullName != other.fullName) return false
        if (specialization != other.specialization) return false

        return true
    }
    /**
     * Generiše hash kod za [Doctor] objekat.
     *
     * Hash kod koristi ista polja kao [equals] kako bi se osigurala konzistentnost:
     * [id], [userId], [fullName] i [specialization].
     *
     * @return Hash kod objekta
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + specialization.hashCode()
        return result
    }


}
