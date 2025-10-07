package com.example.service.termin

import com.example.domain.Termin
import java.time.LocalDate

/**
 * Interfejs u kome su navedene metode za operacije sa bazom vezane za [com.example.domain.Termin]
 * @author Lazar Jankovic
 * @see com.example.domain.Termin
 */
interface TerminServiceInterface {
    /**
     * Metoda za dodavanje termina
     * @param termin Termin koji treba dodati
     * @throws NullPointerException Ako je prosledjeni parametar null
     * @return [Termin] ako je uspesno dodat
     * @return null Ako nije uspesno dodat
     */
    fun addTermin(termin: Termin?): Termin?
    /**
     * Metoda za izmenu termina
     * @param termin Termin koji treba izmeniti
     * @throws NullPointerException Ako je prosledjeni parametar null
     * @return [Termin] ako je uspesno dodat
     * @return null Ako nije uspesno izmenjen
     */
    fun editTermin(termin: Termin?): Termin?
    /**
     * Metoda za brisanje termina
     * @param terminId Termin koji treba obrisati
     * @throws NullPointerException Ako je prosledjeni parametar null
     * @throws IllegalArgumentException Ako je prosledjeni parametar prazan string
     * @return [Boolean] ako je uspesno obrisan true u suprotnom false
     */
    fun deleteTermin(terminId: String?): Boolean

    /**
     * Metoda za pronalazenje termina po id
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan string
     * @param terminId Id termina koji treba pronaci
     * @return Null ako je pronadjen ako ne [Termin] sa tim id
     */
    fun getTerminForId(terminId: String?): Termin?
    /**
     * Metoda za pronalazenje termina za jednog lekara
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan string
     * @param doctorId Id lekara cije termine treba pronaci
     * @return [List[Termin]] Lista termina za tog lekara
     */
    fun getTerminsForDoctor(doctorId:String?): List<Termin>
    /**
     * Metoda za pronalazenje termina za jednog pacijenta
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan string
     * @param patientId Id pacijenta cije termine treba pronaci
     * @return [List[Termin]] Lista termina za tog pacijenta
     */
    fun getTerminsForPatient(patientId: String?): List<Termin>
    /**
     * Metoda za pronalazenje termina za jednog lekara, za odredjeni datum
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan string
     * @param doctorId Id lekara cije termine treba pronaci
     * @param date Datum za koji lekar zeli da sazna termine
     * @return [List[Termin]] Lista termina za tog lekara za odredjeni datum
     */
    fun getTerminsForDoctorForDate(doctorId:String?,date: LocalDate): List<Termin>
    /**
     * Metoda za pronalazenje termina za jednog pacijenta za odredjeni datum
     * @throws NullPointerException ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan string
     * @param patientId Id pacijenta cije termine treba pronaci
     * @param date Datum za koji se traze termini
     * @return [List[Termin]] Lista termina za tog pacijenta za odredjeni datum
     */
    fun getTerminsForPatientForDate(patientId: String?,date: LocalDate): List<Termin>

    /**
     * Metoda za pronalazenje svih termina, koristice se u logickom sloju za proveru
     * pri dodavanju novog termina
     * @return List[Termin]
     */
    fun getAllTermins(): List<Termin>
}