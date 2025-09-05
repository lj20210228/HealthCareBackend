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
     * @throws IllegalArgumentException Ako lekar vec postoji
     * @return [Doctor] ako je dodavanje uspesno kao odgovor vracaju se podaci o lekaru
     */
    suspend fun addDoctor(doctor: Doctor?): Doctor

    /**
     * Funkcija koja pronalazi podatke o lekaru na osnovu njegovog id
     * @param doctorId Id lekara kog treba pronaci
     * @throws NullPointerException Ukoliko je doctorId null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je doctorId prazan string
     * @return [Doctor] Ukoliko se pronadju podaci o lekaru vracaju se, ukoliko ne vraca se null vrednost
     * @return [null] AKo lekar sa zadatim id ne postoji u bazi
     */
    suspend fun getDoctorForId(doctorId: String?): Doctor?
    /**
     * Funkcija koja pronalazi podatke o lekarima jedne bolnice
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorsInHospital(hospitalId: String?): List<Doctor>
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
}