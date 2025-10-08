package com.example.repository.termin

import com.example.domain.Termin
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceInterface
import com.example.service.termin.TerminServiceImplementation
import com.example.service.termin.TerminServiceInterface
import kotlinx.coroutines.flow.merge
import java.time.LocalDate

/**
 * Klasa koja implementira metode [TerminRepository]
 * @author Lazar Jankovic
 * @see TerminRepository
 * @see Termin
 * @param service Servis za komunikaciju sa bazom za termine
 * @param doctorService Servis za komunikaciju sa bazom za lekare
 * @param hospitalService Servis za komunikaciju sa bazom za bolnice
 * @param patientService Servis za komunikaciju sa bazom za pacijente
 */
class TerminRepositoryImplementation(val service: TerminServiceInterface,
    val doctorService: DoctorServiceInterface,
    val patientService: PatientServiceInterface,
    val hospitalService: HospitalServiceInterface): TerminRepository {
    override suspend fun addTermin(termin: Termin?): BaseResponse<Termin> {
        if (termin==null)
            return BaseResponse.ErrorResponse(message = "Termin koji zelite da dodate ne moze biti null")
        val doctorExist=doctorService.getDoctorForId(termin.getDoctorId())
        if (doctorExist==null)
            return BaseResponse.ErrorResponse(message = "Taj lekar ne postoji")
        val patientExist=patientService.getPatientById(termin.getPatientId())
        if (patientExist==null)
            return BaseResponse.ErrorResponse(message = "Pacijent ne postoji")
        val hospitalExist=hospitalService.getHospitalById(termin.getHospitalId())
        if (hospitalExist==null)
            return BaseResponse.ErrorResponse(message = "Bolnica ne postoji")
        val termins=service.getAllTermins()
        if(termins.contains(termin))
            return BaseResponse.ErrorResponse(message = "Termin vec postoji")
        val terminAdded=service.addTermin(termin)
        if (terminAdded==null)
            return BaseResponse.ErrorResponse(message = "Termin nije uspesno dodat")
        return BaseResponse.SuccessResponse(data = terminAdded, message = "Termin uspesno dodat")
    }

    override suspend fun editTermin(termin: Termin?): BaseResponse<Termin> {
        if (termin==null)
            return BaseResponse.ErrorResponse(message = "Termin koji zelite da izmenite ne moze biti null")
        val exist=service.getTerminForId(termin.getId())
        if (exist==null)
            return BaseResponse.ErrorResponse(message = "Termin ne postoji")
        val edited=service.editTermin(termin)
        if (edited==null||!edited.equals(termin))
            return BaseResponse.ErrorResponse(message = "Termin nije uspesno izmenjen")
        return BaseResponse.SuccessResponse(data = edited, message = "Termin uspesno izmenjen")
    }

    override suspend fun deleteTermin(terminId: String?): BaseResponse<Boolean> {
        if (terminId==null)
            return BaseResponse.ErrorResponse(message = "Id termina koji zelite da obrisete ne moze biti null")
        val exist=service.getTerminForId(terminId)
        if (exist==null)
            return BaseResponse.ErrorResponse(message = "Termin ne postoji")
        val deleted=service.deleteTermin(terminId)
        if (deleted==false)
            return BaseResponse.ErrorResponse(message = "Termin nije uspesno obrisan")
        return BaseResponse.SuccessResponse(data = true, message = "Termin uspesno obrisan")
    }

    override suspend fun getTerminForId(terminId: String?): BaseResponse<Termin> {
        if (terminId==null)
            return BaseResponse.ErrorResponse(message = "Id termina koji trazite ne moze biti null")

        val finded=service.getTerminForId(terminId)
        if (finded==null)
            return BaseResponse.ErrorResponse(message = "Termin nije pronadjen")
        return BaseResponse.SuccessResponse(data =finded, message = "Termin pronadjen")
    }

    override suspend fun getTerminsForDoctor(doctorId: String?): ListResponse<Termin> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Id lekara cije termine trazite ne moze biti null")
        val termins=service.getTerminsForDoctor(doctorId)
        if (termins.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj lekar nema termina")
        return ListResponse.SuccessResponse(data = termins, message = "Termini uspesno pronadjeni")
    }

    override suspend fun getTerminsForPatient(patientId: String?): ListResponse<Termin> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Id pacijenta cije termine trazite ne moze biti null")
        val termins=service.getTerminsForPatient(patientId)
        if (termins.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj pacijent nema termina")
        return ListResponse.SuccessResponse(data = termins, message = "Termini uspesno pronadjeni")
    }

    override suspend fun getTerminsForDoctorForDate(
        doctorId: String?,
        date: LocalDate
    ): ListResponse<Termin> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Id lekara cije termine trazite ne moze biti null")
        val termins=service.getTerminsForDoctorForDate(doctorId,date)
        if (termins.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj lekar nema termina")
        return ListResponse.SuccessResponse(data = termins, message = "Termini uspesno pronadjeni")
    }

    override suspend fun getTerminsForPatientForDate(
        patientId: String?,
        date: LocalDate
    ): ListResponse<Termin> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Id pacijenta cije termine trazite ne moze biti null")
        val termins=service.getTerminsForPatientForDate(patientId,date)
        if (termins.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj pacijent nema termina")
        return ListResponse.SuccessResponse(data = termins, message = "Termini uspesno pronadjeni")
    }
}