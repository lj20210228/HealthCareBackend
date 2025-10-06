package com.example.service.workTime

import com.example.domain.DayInWeek
import com.example.domain.Doctor
import com.example.domain.WorkTime

/**
 * Interfejs koji sluzi za rukovanje podacima o [WorkTime]
 * @author Lazar Janković
 * @see WorkTime
 *
 */
interface WorkTimeInterface {

    /**
     * Funkcija koja na osnovu id lekara  vraca njegovo radno vreme,
     * ako lekar nije pronadjen vraca se null, ako ne radi prazna lista
     * @param doctorId Id lekara
     * @throws NullPointerException Ako je [doctorId] null
     * @throws IllegalArgumentException AKo je [doctorId] prazan string
     * @return [List[WorkTime]]  ako je lekar pronadjen vraca se njegovo radno vreme
     * @return null Ako lekar nije pronadjen
     * @return emptyList() Ako lekar ne radi
     */
    suspend fun getWorkTimeForDoctor(doctorId: String?): List<WorkTime>


    /**
     * Funckija koja dodaje radno vreme lekara
     * @param workTime Radno vreme koje treba da se doda
     * @throws NullPointerException Ako je [workTime] null
     * @return [WorkTime] Ako je uspesno dodavanje
     */
    suspend fun addWorkTime(workTime: WorkTime?): WorkTime?
    /**
     * Funckija koja brise radno vreme lekara
     * @param id Id [WorkTime] kog treba obrisati
     * @throws NullPointerException Ako je [id] null
     * @throws IllegalArgumentException Ako je id prazan string
     * @return [WorkTime] Ako je uspesno brisanje
     */
    suspend fun deleteWorkingTime(id: String?): Boolean
    /**
     * Funckija koja azurira radno vreme lekara
     * @param workTime Radno vreme koje treba da se azurira
     * @throws NullPointerException Ako je [workTime] null
     * @return null Ako [workTime] ne postoji
     * @return [WorkTime] Ako je uspesno azuriranje
     */
    suspend fun updateWorkingTime(workTime: WorkTime?): WorkTime?

    /**
     * Metoda koja vraca listu svih radnih vremena kako bi se proverilo prilikom brisanja i dodavanja da li postoji
     * vec radno vreme
     * @return [List[WorkTime]]
     */
    suspend fun getAllWorkingTimes(): List<WorkTime>

    /**
     * Metoda koja vraca radno vreme po id
     * @param id Id radnog vremena
     * @throws NullPointerException Ako je [id] null
     * @throws IllegalArgumentException Ako je id prazan string
     * @return [WorkTime] Podaci o radnom vremenu ili null ako nisu pronadjeni
     */
    suspend fun getWorkingTimeForId(id: String?): WorkTime?


}