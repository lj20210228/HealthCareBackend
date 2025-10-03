package com.example.repository.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.patient.PatientServiceInterface
import com.example.service.selectedDoctor.SelectedDoctorServiceInterface
import kotlinx.coroutines.flow.merge
import org.postgresql.translation.messages_bg

/**
 * Klasa koja implementira [SelectedDoctorRepository]
 * @author Lazar Jankovic
 * @see SelectedDoctorRepository
 * @see PatientServiceInterface
 * @see DoctorServiceInterface
 */
class SelectedDoctorRepositoryImplementation(val service: SelectedDoctorServiceInterface,
    val patientServiceInterface: PatientServiceInterface,val doctorServiceInterface: DoctorServiceInterface): SelectedDoctorRepository {
    override suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): BaseResponse<SelectedDoctor> {
        if (selectedDoctor==null)
            return BaseResponse.ErrorResponse(message = "Niste uneli ispravne podatke za izabranog lekara")
        val listOfSelectedDoctors=service.getAllSelectedDoctors()
        if (listOfSelectedDoctors.contains(selectedDoctor)){
            return BaseResponse.ErrorResponse(message = "Izabrani lekar za ovog pacijenta vec postoji.")
        }
        val selectedDoctorAdded=service.addSelectedDoctorForPatient(selectedDoctor)
        if (selectedDoctorAdded==null){
            return BaseResponse.ErrorResponse(message = "Izabrani lekar nije dodat")
        }
        return BaseResponse.SuccessResponse(data = selectedDoctorAdded, message = "Izabrani lekar uspesno dodat")
    }

    override suspend fun getSelectedDoctorsForPatients(patientId: String?): ListResponse<Doctor> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili podatke o pacijentu")
        val doctors=service.getSelectedDoctorsForPatients(patientId)
        if (doctors.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj pacijent nema ni jednog izabranog lekara")
        return ListResponse.SuccessResponse(data = doctors,"Pronadjeni izabrani lekari")

    }

    override suspend fun getPatientsForSelectedDoctor(doctorId: String?): ListResponse<Patient> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili podatke o pacijentu")
        val patients=service.getPatientsForSelectedDoctor(doctorId)
        if (patients.isEmpty())
            return ListResponse.ErrorResponse(message = "Ovaj lekar nije izabran nikome")
        return ListResponse.SuccessResponse(data = patients,"Pronadjeni pacijenti")
    }

    override suspend fun editSelectedDoctor(selectedDoctor: SelectedDoctor?): BaseResponse<SelectedDoctor> {
        if (selectedDoctor==null)
            return BaseResponse.ErrorResponse(message = "Prosledili ste neispravne podatke")
        val patient=patientServiceInterface.getPatientById(selectedDoctor.getPatientId())
        if(patient==null)
            return BaseResponse.ErrorResponse(message = "Pacijent ne postoji")
        val doctor=doctorServiceInterface.getDoctorForId(selectedDoctor.getDoctorId())

        if (doctor==null){
            return BaseResponse.ErrorResponse(message = "Lekar ne postoji")
        }
        val result=service.editSelectedDoctor(selectedDoctor)
        if (result==null)
            return BaseResponse.ErrorResponse(message = "Izabrani lekar izmenjen neuspesno")
        return BaseResponse.SuccessResponse(message = "Izabrani lekar izmenjen uspesno", data = result)
    }

    override suspend fun deleteSelectedDoctor(selectedDoctor: SelectedDoctor?): BaseResponse<Boolean> {
        if (selectedDoctor==null)
            return BaseResponse.ErrorResponse(message = "Prosledili ste neispravne podatke za brisanje")
        val result=service.deleteSelectedDoctor(selectedDoctor)
        if (result==false)
            return BaseResponse.ErrorResponse(message = "Izabrani lekar nije uspesno obrisan")
        return BaseResponse.SuccessResponse(message = "Izabrani lekar uspesno obrisan",data=result)
    }
}