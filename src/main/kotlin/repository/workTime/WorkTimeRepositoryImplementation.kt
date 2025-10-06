package com.example.repository.workTime

import com.example.domain.WorkTime
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.workTime.WorkTimeInterface
import org.jetbrains.exposed.sql.idParam

/**
 * Klasa koja implementira [WorkTimeRepository]
 * @see WorkTimeRepository
 * @see WorkTime
 * @author Lazar Jankovic
 * @param service Interfejs koji sadrzi metode za operacije sa bazom za [WorkTime]
 * @param doctorService Interfejs koji sadrzi metode za operacije sa bazom za [com.example.domain.Doctor]
 */
class WorkTimeRepositoryImplementation(val service: WorkTimeInterface,
    val doctorService: DoctorServiceInterface): WorkTimeRepository {
    override suspend fun getWorkTimeForDoctor(doctorId: String?): ListResponse<WorkTime> {
        if(doctorId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili podatke o lekaru")
        val exist=doctorService.getDoctorForId(doctorId)
        if (exist==null)
            return ListResponse.ErrorResponse(message = "Lekar cije radno vreme trazite ne postoji")
        val response=service.getWorkTimeForDoctor(doctorId)
        if (response.isEmpty())
            return ListResponse.ErrorResponse(message = "Lekar nije oznacio svoje radno vreme")
        return ListResponse.SuccessResponse(message = "Radno vreme uspesno pronadjeno", data = response)
    }

    override suspend fun addWorkTime(workTime: WorkTime?): BaseResponse<WorkTime> {
        if (workTime==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili podatke o radnom vremenu")
        val doctorExist=doctorService.getDoctorForId(workTime.getDoctorId())
        if (doctorExist==null){
            return BaseResponse.ErrorResponse(message = "Lekar ne postoji")
        }
        val list=service.getAllWorkingTimes()
        if (list.contains(workTime))
            return BaseResponse.ErrorResponse(message = "Radno vreme vec postoji")

        val workTime=service.addWorkTime(workTime)
        if (workTime==null)
            return BaseResponse.ErrorResponse(message = "Radno vreme neuspesno dodato")

        return BaseResponse.SuccessResponse(data = workTime, message = "Radno vreme uspesno dodato")

    }

    override suspend fun deleteWorkingTime(id: String?): BaseResponse<Boolean> {
        if (id==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili podatke za brisanje radnog vremena")
        val exist=service.getWorkingTimeForId(id)
        if (exist==null)
            return BaseResponse.ErrorResponse(message = "Ne mozete da obrisete radno vreme koje ne postoji")
        val deleted=service.deleteWorkingTime(id)
        if (!deleted)
            return BaseResponse.ErrorResponse(message = "Radno vreme nije uspesno obrisano")
        return BaseResponse.SuccessResponse(data = true, message = "Radno vreme uspesno obrisano")
    }

    override suspend fun updateWorkingTime(workTime: WorkTime?): BaseResponse<WorkTime> {
        if (workTime==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili ispravne podatke za azuriranje")
        val exist=service.getWorkingTimeForId(workTime.getId())
        if(exist==null)
            return BaseResponse.ErrorResponse(message = "Radno vreme koje pokusavate da azurirate ne postoji")
        val updated=service.updateWorkingTime(workTime)
        if (updated==null||!updated.equals(workTime))
            return BaseResponse.ErrorResponse(message = "Radno vreme neuspesno azurirano")
        return BaseResponse.SuccessResponse(data = updated, message = "Radno vreme uspesno azurirano")

    }
}