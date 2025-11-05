package com.example.service.patient

import com.example.domain.Patient

/**
 * Interfejs koji sluzi za rukovanje podacima o pacijentima
 * @author Lazar Janković
 * @see Patient
 */
interface PatientServiceInterface {

    /**
     * Metoda za dodavanje novog pacijenta
     * @param patient Podaci o pacijentu kojeg treba dodati
     * @return Ako je pacijent uspesno dodat vracaju se podaci o njemu kao
     * [Patient] objekat
     * @throws NullPointerException Ako je [patient] null baca se izuzetak
     * @throws IllegalArgumentException Ako pacijent vec postoji
     */
    suspend fun addPatient(patient: Patient?): Patient?

    /**
     * Funkcija za pronalazak pacijenta po njegovom Id
     * @param patientId Id pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [Patient] koji sadrzi podatke o njemu,
     * ukoliko pacijent nije pronadjen vraca se null
     * @throws NullPointerException ako je [patientId] null
     * @throws IllegalArgumentException ako je [patientId] prazan string
     */
    suspend fun getPatientById(patientId: String?): Patient?
    /**
     * Funkcija za pronalazak pacijenta po userId
     * @param  userId User id pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [Patient] koji sadrzi podatke o njemu,
     * ukoliko pacijent nije pronadjen vraca se null
     * @throws NullPointerException ako je [patientId] null
     * @throws IllegalArgumentException ako je [patientId] prazan string
     */
    suspend fun getPatientByUserId(userId: String?): Patient?
    /**
     * Funkcija za pronalazak pacijenta po njegovom JMBG
     * @param jmbg JMBG pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [Patient] koji sadrzi podatke o njemu,
     * ukoliko pacijent nije pronadjen vraca se null
     * @throws NullPointerException ako je [jmbg] null
     * @throws IllegalArgumentException ako je [jmbg] prazan string
     * @throws IllegalArgumentException ako  [jmbg] ne sadrzi samo brojeve
     * @throws IllegalArgumentException ako je [jmbg] duzine razlicite od 13
     */
    suspend fun getPatientByJmbg(jmbg: String?): Patient?


    /**
     * Funkcija koja vraca sve pacijente iz jedne bolnice
     * @param hospitalId Id bolnice cije pacijente trazimo
     * @return [List[Patient]] Ukoliko se pronadju podaci o pacijentu vraca se lista pacijenata,
     * ukoliko ne vraca se prazna lista
     * @throws NullPointerException Ukoliko je [hospitalId] null
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     */
    suspend fun getAllPatientInHospital(hospitalId: String?): List<Patient>

    /**
     * Funkcija za izmenu podataka o pacijentu
     * @param patient Podaci o pacijentu koje treba izmeniti
     * @return null Ako su podaci neuspesno azurirani
     * @return [Patient] Ako su podaci uspesno azurirani
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako pacijent ne postoji
     */
    suspend fun editPatient(patient: Patient?): Patient?

    /**
     * Funkcija koja brise podatke o pacijentu sa odredjenih id
     * @param patientId Id pacijenta kog treba obrisati
     * @throws NullPointerException Ako je prosledjeni parametar null
     * @throws IllegalArgumentException Ako je prosledjeni id prazan stirng
     * @throws IllegalArgumentException Ako pacijent ne postoji
     * @return [Boolean] Ako je pacijent uspesno obrisan true, u suprotnom false
     */
    suspend fun deletePatient(patientId: String?): Boolean

    /**
     * Funckija za vracanje svih pacijenata, koristice se za vec postoji slucaj
     */
    suspend fun getAllPatients(): List<Patient>


}