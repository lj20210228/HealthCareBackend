package com.example.repository.doctor

import com.example.domain.Doctor
import com.example.request.DoctorRequest
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Interfejs koji sluzi za rukovanje podataka o lekarima na logickom sloju
 * @author Lazar Janković
 * @see Doctor
 */
interface DoctorRepository {

    /**
     * Funkcija koja prosledjuje servisnom sloju podatke za dodavanje lekara
     * @param doctor Podaci o lekaru kog treba dodati
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException AKo lekar vec postoji
     * @return  [BaseResponse<Doctor>]  ako je dodavanje uspesno kao odgovor vracaju se podaci o lekaru sa porukom da je uspesno ili poruka o gresci
     */
    suspend fun addDoctor(doctor: DoctorRequest?): BaseResponse<Doctor>

    /**
     * Funkcija koja prosledjuje servisnom sloju zahtev za pronalazenje lekara
     * @param doctorId Id lekara kog treba pronaci
     * @throws NullPointerException Ukoliko je doctorId null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je doctorId prazan string
     * @return [BaseResponse<Doctor>] Ukoliko se pronadju podaci o lekaru vracaju se sa porukom da je uspesno ili odgovor da nije pronadjeno
     */
    suspend fun getDoctorForId(doctorId: String?): BaseResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke o lekarima
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorsInHospital(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke o opstim lekarima jedne bolnice, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se poruka o gresci
     */
    suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke podatke o lekarima jedne bolnice, koji su lekari opste prakse
     * @param hospitalId Id bolnice cije lekare opste prakse treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] prazan string
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima opste prakse vracaju se, ukoliko ne vraca se poruka o gresci
     */
    suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju podatke o lekarima odredjene specijalizacije jedne bolnice
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @param specialization Specijalizacija ciji se lekari traze
     * @throws NullPointerException Ukoliko je [hospitalId] ili [specialization] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] ili [specialization] prazan string
     * @return [List[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorInHospitalForSpecialization(hospitalId: String?,specialization: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje servisnom sloju zahtev za podatke  lekarima jedne bolnice odredjene specijalizacije, koji mogu da budu izabrani i dalje
     * @param hospitalId Id bolnice cije lekare treba pronaci
     * @throws NullPointerException Ukoliko je [hospitalId] ili[specialization]] null baca se izuzetak
     * @throws IllegalArgumentException Ukoliko je [hospitalId] ili [specialization] prazan string
     * @return [ListResponse[Doctor]] Ukoliko se pronadju podaci o lekarima vracaju se, ukoliko ne vraca se prazna lista
     */
    suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId: String?,specialization: String?): ListResponse<Doctor>

    /**
     * Funkcija koja salje servisnom sloju zahtev za  povecavanje  broja trenutnih pacijenata za 1
     * @param doctorId Id lekara cije podatke treba azurirati
     * @throws NullPointerException Id lekara null
     * @throws IllegalArgumentException Id lekara prazan
     * @throws IllegalArgumentException Ukoliko je broj trenutnih pacijenata jednak maksimalnom
     * @return [Boolean] vrednost, ukoliko je uspesno vraca se poruka o uspesnosti u suprotnom o neuspesnosti
     */
    suspend fun editCurrentPatients(doctorId: String?): BaseResponse<Boolean>
}