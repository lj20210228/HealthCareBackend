package com.example.service

import com.example.domain.Doctor


/**
 * Interfejs koji sluzi za rukovanje podataka o lekarima
 * @author Lazar Janković
 * @see Doctor
 */
interface DoctorServiceInterface {

    /**
     * Funkcija koja sluzi za dodavanje lekara u bazu
     * @param doctor Podaci o lekaru kog treba dodati
     * @throws NullPointerException Ako je prosledjeni argument null
     * @return [Doctor] ako je dodavanje uspesno kao odgovor vracaju se podaci o lekaru
     */
    suspend fun addDoctor(doctor: Doctor?): Doctor?

    /**
     * Funkcija koja pronalazi podatke o lekaru na osnovu njegovog id
     * @param doctorId Id lekara kog treba pronaci
     * @throws NullPointerException Ukoliko je doctorId null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je doctorId prazan string
     * @return [Doctor] Ukoliko se pronadju podaci o lekaru vracaju se, ukoliko ne vraca se null vrednost
     */
    suspend fun getDoctorForId(doctorId: String?): Doctor?
    /**
     * Funkcija koja pronalazi podatke o lekaru na osnovu njegovog userId
     * @param userId Id lekara kog treba pronaci
     * @throws NullPointerException Ukoliko je doctorId null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je doctorId prazan string
     * @return [Doctor] Ukoliko se pronadju podaci o lekaru vracaju se, ukoliko ne vraca se null vrednost
     */
    suspend fun getDoctorForUserId(userId: String?): Doctor?
    /**
     * Funkcija koja pronalazi podatke o lekarima jedne bolnice
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorsInHospital(hospitalId: String?): List<Doctor>
    /**
     * Funkcija koja pronalazi podatke o opstim lekarima jedne bolnice, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): List<Doctor>
    /**
     * Funkcija koja pronalazi podatke o lekarima jedne bolnice, koji su lekari opste prakse
     * @param hospitalId Id bolnice cije lekare opste prakse treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima opste prakse vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): List<Doctor>
    /**
     * Funkcija koja pronalazi podatke o lekarima odredjene specijalizacije jedne bolnice
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @param specialization Specijalizacija ciji se lekari traze
     * @throws NullPointerException Ukoliko je [hospitalId] ili [specialization] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] ili [specialization] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorInHospitalForSpecialization(hospitalId: String?,specialization: String?): List<Doctor>
    /**
     * Funkcija koja pronalazi podatke  lekarima jedne bolnice odredjene specijalizacije, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] ili[specialization]] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] ili [specialization] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId: String?,specialization: String?): List<Doctor>

    /**
     * Funkcija koja povecava broj trenutnih pacijenata za 1
     * @param doctorId Id lekara cije podatke treba azurirati
     * @throws NullPointerException Id lekara null
     * @throws IllegalArgumentException Id lekara prazan
     * @throws IllegalArgumentException Ukoliko je broj trenutnih pacijenata jednak maksimalnom
     * @return [Boolean] vrednost, ukoliko je uspesno azurirano true, u suprtonom false
     */
    suspend fun editCurrentPatients(doctorId: String?): Boolean


    /**
     * Funkcija za pronalazenje svih lekara, sluzice za vec postoji slucaj
     */
    suspend fun getAllDoctors(): List<Doctor>
}