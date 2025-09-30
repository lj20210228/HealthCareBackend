package com.example.repository.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.selectedDoctor.SelectedDoctorServiceInterface

class SelectedDoctorRepositoryImplementation(val service: SelectedDoctorServiceInterface): SelectedDoctorRepository {
    override suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): BaseResponse<SelectedDoctor> {
        if (selectedDoctor==null)
            return BaseResponse.ErrorResponse("Niste uneli ispravne podatke za izabranog lekara")
        val listOfSelectedDoctors=service.getAllSelectedDoctors()
        if (listOfSelectedDoctors.contains(selectedDoctor)){
            return BaseResponse.ErrorResponse(message = "Izabrani lekar za ovog pacijenta vec postoji.")
        }
        val selectedDoctorAdded=service.addSelectedDoctorForPatient(selectedDoctor)
        if (selectedDoctorAdded==null){
            return BaseResponse.ErrorResponse("Izabrani lekar nije dodat")
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
}