package com.example.repository.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Interfejs koji sluzi za rukovanje podacima o izabranim lekarima na logickom sloju
 *
 *@see com.example.service.selectedDoctor.SelectedDoctorServiceInterface
 * @author Lazar Janković
 * @see Patient
 * @see Doctor
 */
interface SelectedDoctorRepository {
    /**
     * Funkcija koja salje zahtev za dodavanje  izabranog lekara za pacijenta
     * @param selectedDoctor Prosledjeni parametar gde se nalaze id pacijenta i lekara
     * @return [SelectedDoctor] Vracaju se podaci o uspesnom dodavanju, id lekara i pacijenta,
     * ako ne poruka o gresci
     */
    suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): BaseResponse<SelectedDoctor>

    /**
     * Funkcija koja salje zahtev za vracanje svih izabranih lekara za  jednog pacijenta
     * @param patientId Prosledjeni id pacijenta
     * @see Patient
     * @see Doctor
     * @return [ListResponse<Doctor>] Ukoliko postoje izabrani lekari vraca se lista u suprotnom poruka o gresci
     */
    suspend fun getSelectedDoctorsForPatients(patientId: String?): ListResponse<Doctor>
    /**
     * Funkcija koja salje zahtev za vracanje svih pacijenata kojima je lekar izabrani
     * @param doctorId Prosledjeni id lekara
     * @see Patient
     * @see Doctor
     * @return [ListResponse<Doctor>] Ukoliko postoje pacijenti kojima je lekar izabrani vraca se lista, ako ne poruka o gresci
     */
    suspend fun getPatientsForSelectedDoctor(doctorId: String?): ListResponse<Patient>

}