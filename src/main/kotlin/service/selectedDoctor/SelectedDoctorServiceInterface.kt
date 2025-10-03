package com.example.service.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor

/**
 * Interfejs koji sluzi za rukovanje podacima o izabranim lekarima
 *
 * @author Lazar Janković
 * @see Patient
 * @see Doctor
 */
interface SelectedDoctorServiceInterface {
    /**
     * Funkcija koja dodaje izabranog lekara za pacijenta
     * @param selectedDoctor Prosledjeni parametar gde se nalaze id pacijenta i lekara
     * @see Patient
     * @see Doctor
     * @throws NullPointerException Ukoliko je [selectedDoctor] null
     * @throws IllegalArgumentException Ukoliko vec postoji polje sa istim podacima
     * @return [SelectedDoctor] Vracaju se podaci o uspesnom dodavanju, id lekara i pacijenta
     */
    suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): SelectedDoctor?

    /**
     * Funkcija koja vraca sve izabrane lekare jednog pacijenta
     * @param patientId Prosledjeni id pacijenta
     * @see Patient
     * @see Doctor
     * @throws NullPointerException Ukoliko je [patientId] null
     * @throws IllegalArgumentException Ukoliko je [patientId] prazan string
     * @return [List[Doctor]] Ukoliko postoje izabrani lekari vraca se lista u suprotnom prazna lista
     */
    suspend fun getSelectedDoctorsForPatients(patientId: String?): List<Doctor>
    /**
     * Funkcija koja vraca sve pacijente kojima je lekar izabrani
     * @param doctorId Prosledjeni id lekara
     * @see Patient
     * @see Doctor
     * @throws NullPointerException Ukoliko je [doctorId] null
     * @throws IllegalArgumentException Ukoliko je [doctorId] prazan string
     * @return [List[Doctor]] Ukoliko postoje pacijenti kojima je lekar izabrani vraca se lista, ako ne prazna lista
     */
    suspend fun getPatientsForSelectedDoctor(doctorId: String?): List<Patient>

    /**
     * Funkcija koja menja izabranog lekara za odredjenog pacijenta
     * @param selectedDoctor Novi podaci o izabranom lekaru za pacijenta
     * @see SelectedDoctor
     * @see Patient
     * @see Doctor
     * @throws NullPointerException Ukoliko je prosledjeni parametar null
     * @return null Ako nije pronadjen, ako jeste vracaju se izmenjeni podaci
     */
    suspend fun editSelectedDoctor(selectedDoctor: SelectedDoctor?): SelectedDoctor?


    /**
     * Funkcija koja brise podatke o izabranom lekaru za odrenjenog pacijenta
     * @param selectedDoctor Podaci koje treba obrisati
     * @return Ukoliko je uspesno obrisan vraca se true o njemu, ukoliko ne false
     */
    suspend fun deleteSelectedDoctor(selectedDoctor: SelectedDoctor?): Boolean



    /**
     * Funckija koja vraca sve izabrane lekare, koja ce se koristiti za proveru da li lekar vec postoji
     * @return [List[SelectedDoctor]]
     */
    suspend fun getAllSelectedDoctors():List<SelectedDoctor>

}