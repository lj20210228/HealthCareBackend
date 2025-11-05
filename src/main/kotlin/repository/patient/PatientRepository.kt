package com.example.repository.patient

import com.example.domain.Patient
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Interfejs koji sluzi za rukovanje podacima o pacijentima na logickom sloju
 * @author Lazar Janković
 * @see Patient
 * @see com.example.service.patient.PatientServiceInterface
 * @see BaseResponse
 * @see com.example.response.ListResponse
 */
interface PatientRepository {

    /**
     * Metoda koja salje servisnom sloju zahtev za dodavanje novog pacijenta
     * @param patient Podaci o pacijentu kojeg treba dodati
     * @return Ako je pacijent uspesno dodat vracaju se podaci o njemu kao
     * [BaseResponse] objekat i poruka o upesnosti ili poruka o neuspesnosti
     */
    suspend fun addPatient(patient: Patient?): BaseResponse<Patient>

    /**
     * Funkcija za slanje zahteva servisnom sloju za pronalazak pacijenta po njegovom Id
     * @param patientId Id pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [BaseResponse<Patient>] koji sadrzi podatke o njemu i poruku da je pronadjen
     * , ili poruku o neuspesnosti
     */
    suspend fun getPatientById(patientId: String?): BaseResponse<Patient>
    /**
     * Funkcija za slanje zahteva servisnom sloju za pronalazak pacijenta po njegovom userId
     * @param userId User id pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [BaseResponse<Patient>] koji sadrzi podatke o njemu i poruku da je pronadjen
     * , ili poruku o neuspesnosti
     */
    suspend fun getPatientByUserId(userId: String?): BaseResponse<Patient>
    /**
     * Funkcija  za slanje zahteva za pronalazak pacijenta za pronalazak pacijenta po njegovom JMBG
     * @param jmbg JMBG pacijenta
     * @return  Ako je pacijent pronadjen vraca se objekat [BaseResponse<Patient>] koji sadrzi podatke o njemu,
     * ukoliko pacijent nije pronadjen vraca se null
     */
    suspend fun getPatientByJmbg(jmbg: String?): BaseResponse<Patient>


    /**
     * Funkcija koja salje zahtev za vracnje svih pacijente iz jedne bolnice
     * @param hospitalId Id bolnice cije pacijente trazimo
     * @return [ListResponse[Patient]] Ukoliko se pronadju podaci o pacijentu vraca se lista pacijenata,
     * ukoliko ne vraca se poruka da nisu proadjeni
     */
    suspend fun getAllPatientInHospital(hospitalId: String?): ListResponse<Patient>

    /**
     * Funkcija za slanje zahteva za izmenu podataka o pacijentu
     * @param patient Podaci o pacijentu koje treba izmeniti
     * @return [BaseResponse<Patient>] Ako su podaci uspesno azurirani ili poruka o gresci ako nisu

     */
    suspend fun editPatient(patient: Patient?): BaseResponse<Patient>

    /**
     * Funkcija koja salje servisnom sloju zahtev za brisanje pacijenta sa odredjenih id
     * @param patientId Id pacijenta kog treba obrisati
     * @return [BaseResponse<Boolean>] Ako je pacijent uspesno obrisan poruka o uspesnosti, u suprotnom o neuspesnosti
     */
    suspend fun deletePatient(patientId: String?): BaseResponse<Boolean>


}