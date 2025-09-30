package com.example.repository.hospital

import com.example.domain.Hospital
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Intefejs koji sadrzi metode za rukovanje podacima o bolnici na logickom sloju
 * @see com.example.service.hospital.HospitalServiceInterface
 * @see com.example.response.BaseResponse
 * @see com.example.response.ListResponse
 * @author Lazar Janković
 * @see com.example.domain.Hospital
 */
interface HospitalRepository {
    /**
     * Funkcija za slanje zahteva dodavanje nove bolnice
     * @param hospital Bolnica koju treba dodati
     * @return [BaseResponse<Hospital>] Ukoliko je bolnica uspesno dodata vracaju se podaci o njoj,
     * ukoliko nije ili vec postoji ili je prosledjeni argument null vraca se poruka o gresci
     */
    suspend fun addHospital(hospital: Hospital?): BaseResponse<Hospital>

    /**
     * Metoda za slanje zahteva za pretrazivanje bolnice po njenom id
     * @param hospitalId Id bolnice koju treba pronaci
     * @return [BaseResponse<Hospital>] Ukoliko je bolnica uspesno pronadjena vraca se objekat klase [Hospital],
     * ukoliko nije vraca se poruka o gresci
     */
    suspend fun getHospitalById(hospitalId: String?): BaseResponse<Hospital>
    /**
     * Metoda za slanje zahteva za pretrazivanje bolnice po njenom imenu
     * @param name Ime bolnice koju treba pronaci
     * @return [Hospital] Ukoliko je bolnica uspesno pronadjena vraca se objekat klase [Hospital],
     * ukoliko nije vraca se poruka o gresci
     */
    suspend fun getHospitalByName(name: String?): BaseResponse<Hospital>
    /**
     * Metoda za  zahtev za pretrazivanje bolnica imenu grada u kom se nalazi
     * @param city Ime grada cije bolnice treba pronaci
     * @throws NullPointerException Ukoliko je [city] null baca se  izuzetak
     * @throws IllegalArgumentException Ukoliko je [city] prazan string baca se izuzetak
     * @return [ListResponse<Hospital>] Ukoliko su bolnice uspesno pronadjena vraca se lista objekata klase [Hospital],
     * ukoliko ne vraca se poruka o gresci
     */
    suspend fun getHospitalsInCity(city: String?): ListResponse<Hospital>

    /**
     * Metoda koja salje zahtev za vracanje svih bolnica
     * @return [ListResponse<Hospital>]] Ukoliko su bolnice uspesno pronadjene vraca se lista objekata klase [Hospital],
     * ukoliko ne vraca se poruka o gresci
     */
    suspend fun getAllHospitals(): ListResponse<Hospital>

}