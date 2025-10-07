package com.example.repository.termin

import com.example.domain.Termin
import com.example.response.BaseResponse
import com.example.response.ListResponse
import java.time.LocalDate

/**
 * Interfejs u kome su navedene metode za operacije vezane za [com.example.domain.Termin] na logickom nivou
 * @author Lazar Jankovic
 * @see com.example.domain.Termin
 * @see com.example.service.termin.TerminServiceInterface
 */
interface TerminRepository {
    /**
     * Metoda za slanje zahteva dodavanje termina
     * @param termin Termin koji treba dodati
     * @return [BaseResponse[Termin]] ako je uspesno dodat podaci o terminu, ako ne poruka o gresci
     */
    suspend fun addTermin(termin: Termin?): BaseResponse<Termin>
    /**
     * Metoda za slanje zahteva izmenu termina
     * @param termin Termin koji treba izmeniti
     * @return [BaseResponse[Termin]] ako je uspesno dodat, ako ne poruka o gresci
     */
    suspend fun editTermin(termin: Termin?): BaseResponse<Termin>
    /**
     * Metoda za brisanje termina
     * @param terminId Termin koji treba obrisati
     * @return [BaseResponse[Boolean]] ako je uspesno obrisan true u suprotnom false i poruka o gresci
     */
    suspend fun deleteTermin(terminId: String?): BaseResponse<Boolean>

    /**
     * Metoda za pronalazenje termina po id
     * @param terminId Id termina koji treba pronaci
     * @return [BaseResponse[Termin]] Ako je pro
     */
    suspend fun getTerminForId(terminId: String?): BaseResponse<Termin>
    /**
     * Metoda za slanje zahteva za pronalazenje termina za jednog lekara
     * @param doctorId Id lekara cije termine treba pronaci
     * @return [ListResponse[Termin]] Lista termina za tog lekara ili poruka o gresci
     */
    suspend fun getTerminsForDoctor(doctorId:String?): ListResponse<Termin>
    /**
     * Metoda za slanje zahteva pronalazenje termina za jednog pacijenta
     * @param patientId Id pacijenta cije termine treba pronaci
     * @return [ListResponse[Termin]] Lista termina za tog pacijenta ili poruka o gresci
     */
    suspend fun getTerminsForPatient(patientId: String?): ListResponse<Termin>
    /**
     * Metoda za slanje zahteva pronalazenje termina za jednog lekara, za odredjeni datum
     * @param doctorId Id lekara cije termine treba pronaci
     * @param date Datum za koji lekar zeli da sazna termine
     * @return [ListResponse[Termin]] Lista termina za tog lekara za odredjeni datum ili poruka o gresci
     */
    suspend fun getTerminsForDoctorForDate(doctorId:String?,date: LocalDate): ListResponse<Termin>
    /**
     * Metoda za slanje zahteva za pronalazenje termina za jednog pacijenta za odredjeni datum
     * @param patientId Id pacijenta cije termine treba pronaci
     * @param date Datum za koji se traze termini
     * @return [ListResponse[Termin]] Lista termina za tog pacijenta za odredjeni datum
     */
    suspend fun getTerminsForPatientForDate(patientId: String?,date: LocalDate): ListResponse<Termin>




}