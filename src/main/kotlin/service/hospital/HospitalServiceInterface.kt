package com.example.service.hospital

import com.example.domain.Hospital

/**
 * Intefejs koji sadrzi metode za rukovanje podacima o bolnici
 * @author Lazar Janković
 * @see com.example.domain.Hospital
 */
interface HospitalServiceInterface {
    /**
     * Funkcija za dodavanje nove bolnice
     * @param hospital Bolnica koju treba dodati
     * @throws NullPointerException Ukoliko je [hospital] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko bolnica vec postoji
     * @return [Hospital] Ukoliko je bolnica uspesno dodata vracaju se podaci o njoj
     */
    suspend fun addHospital(hospital: Hospital?): Hospital

    /**
     * Metoda za pretrazivanje bolnice po njenom id
     * @param hospitalId Id bolnice koju treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se  izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string baca se izuzetak
     * @return [Hospital] Ukoliko je bolnica uspesno pronadjena vraca se objekat klase [Hospital]
     */
    suspend fun getHospitalById(hospitalId: String?): Hospital
    /**
     * Metoda za pretrazivanje bolnice po njenom imenu
     * @param name Ime bolnice koju treba pronaci
     * @throws NullPointerException Ukoliko je [name] null baca se  izuzetak
     * @throws IllegalArgumentException Ukoliko je [name] prazan string baca se izuzetak
     * @return [Hospital] Ukoliko je bolnica uspesno pronadjena vraca se objekat klase [Hospital]
     */
    suspend fun getHospitalByName(name: String?): Hospital
    /**
     * Metoda za pretrazivanje bolnica imenu grada u kom se nalazi
     * @param city Ime grada cije bolnice treba pronaci
     * @throws NullPointerException Ukoliko je [city] null baca se  izuzetak
     * @throws IllegalArgumentException Ukoliko je [city] prazan string baca se izuzetak
     * @return [List[Hospital]] Ukoliko su bolnice uspesno pronadjena vraca se lista objekata klase [Hospital]
     */
    suspend fun getHospitalsInCity(city: String?): List<Hospital>

    /**
     * Metoda koja vraca sve bolnice
     * @return [List[Hospital]] Ukoliko su bolnice uspesno pronadjene vraca se lista objekata klase [Hospital]
     */
    suspend fun getAllHospitals(): List<Hospital>

}