package com.example.repository.doctor

import com.example.domain.Doctor
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Interfejs koji sluzi za rukovanje podataka o lekarima na logickom sloju
 * @author Lazar Janković
 * @see Doctor
 * @see BaseResponse
 * @see ListResponse
 */
interface DoctorRepository {

    /**
     * Funkcija koja prosledjuje servisnom sloju podatke za dodavanje lekara
     * @param doctor Podaci o lekaru kog treba dodati
     * @return  [BaseResponse<Doctor>]  ako je dodavanje uspesno kao odgovor vracaju se podaci o lekaru sa porukom da je uspesno ili poruka o gresci
     */
    suspend fun addDoctor(doctor: Doctor?): BaseResponse<Doctor>

    /**
     * Funkcija koja prosledjuje servisnom sloju zahtev za pronalazenje lekara
     * @param doctorId Id lekara kog treba pronaci
    * @return [BaseResponse<Doctor>] Ukoliko se pronadju podaci o lekaru vracaju se sa porukom da je uspesno ili odgovor da nije pronadjeno,
     */
    suspend fun getDoctorForId(doctorId: String?): BaseResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke o lekarima
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista,
     * ili se javlja greska ako bolnica ne postoji
     */
    suspend fun getAllDoctorsInHospital(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke o opstim lekarima jedne bolnice, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se poruka o gresci,
     * ili se javlja greska ako bolnica ne postoji
     */
    suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke podatke o lekarima jedne bolnice, koji su lekari opste prakse
     * @param hospitalId Id bolnice cije lekare opste prakse treba pronaci
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima opste prakse vracaju se, ukoliko ne vraca se poruka o gresci,
     * ili se javlja greska ako bolnica ne postoji
     */
    suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju podatke o lekarima odredjene specijalizacije jedne bolnice
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @param specialization Specijalizacija ciji se lekari traze
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista,
     * ili se javlja greska ako bolnica ne postoji
     */
    suspend fun getAllDoctorInHospitalForSpecialization(hospitalId: String?,specialization: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke  lekarima jedne bolnice odredjene specijalizacije, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista,
     * ili se javlja greska ako bolnica ne postoji
     */
    suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId: String?,specialization: String?): ListResponse<Doctor>

    /**
     * Funkcija koja salje servisnom sloju zahtev za  povecavanje  broja trenutnih pacijenata za 1
     * @param doctorId Id lekara cije podatke treba azurirati
     * @return [Boolean] vrednost, ukoliko je uspesno vraca se poruka o uspesnosti u suprotnom o neuspesnosti
     */
    suspend fun editCurrentPatients(doctorId: String?): BaseResponse<Boolean>
}