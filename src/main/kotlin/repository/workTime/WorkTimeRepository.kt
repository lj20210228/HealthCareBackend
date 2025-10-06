package com.example.repository.workTime

import com.example.domain.WorkTime
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Interfejs koji sluzi za rukovanje o podacima [com.example.service.workTime.WorkTimeInterface] na logickom sloju
 * @author Lazar Janković
 * @see WorkTime
 * @see com.example.service.workTime.WorkTimeInterface
 *
 */
interface WorkTimeRepository {

    /**
     * Funkcija koja salje zahtev za vracanje podataka o radnom vremenu lekara
     * @param doctorId Id lekara
     * @return [ListResponse[WorkTime]]  ako je lekar pronadjen vraca se njegovo radno vreme,
     * ako nije ili nema podataka o njegovom radnom vremenu vraca se poruka o gresci
     */
    suspend fun getWorkTimeForDoctor(doctorId: String?): ListResponse<WorkTime>


    /**
     * Funckija koja salje zahtev za dodavanje radnog vremena lekara
     * @param workTime Radno vreme koje treba da se doda
     * @return [WorkTime] Ako je uspesno dodavanje, ako ne vraca se poruka o gresci
     */
    suspend fun addWorkTime(workTime: WorkTime?): BaseResponse<WorkTime>
    /**
     * Funckija koja salje zahtev za  brisanje radnog vremena lekara
     * @param id Id [WorkTime] kog treba obrisati
     * @return [BaseResponse<Boolean>] Ako je uspesno brisanje vraca se true i poruka o uspesnosti,
     * ako nije poruka o gresci
     */
    suspend fun deleteWorkingTime(id: String?): BaseResponse<Boolean>
    /**
     * Funckija koja salje zahtev za azuriranje radnog vremena lekara
     * @param workTime Radno vreme koje treba da se azurira
     * @return [BaseResponse<WorkingTime>] Ako je uspesno azuriranje,
     * ako nije vraca se poruka o gresci
     */
    suspend fun updateWorkingTime(workTime: WorkTime?): BaseResponse<WorkTime>
}