package com.example.repository.patient

import com.example.domain.Patient
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceInterface

/**
 * Implementacija interfejsa [PatientRepository]
 * @param service Servis gde su implementirane metode za komunikaciju sa bazom za [Patient]
 * @param hospitalService Servis gde su implementirane metodu za komunikaciju sa bazom za [com.example.domain.Hospital]
 * @see PatientRepository
 * @see BaseResponse
 * @see com.example.response.ListResponse
 * @author Lazar Janković
 */
class PatientRepositoryImplementation(val service: PatientServiceInterface
,val hospitalService: HospitalServiceInterface): PatientRepository {
    override suspend fun addPatient(patient: Patient?): BaseResponse<Patient> {
        if (patient==null){
            return BaseResponse.ErrorResponse(message = "Pacijent koji se dodaje mora imati vrenost")
        }
        val patient=service.addPatient(patient)
        if (patient==null)
            return BaseResponse.ErrorResponse(message = "Pacijent nije uspesno dodat u bazu")
        return BaseResponse.SuccessResponse(data = patient, message = "Pacijent uspesno dodat")
    }

    override suspend fun getPatientById(patientId: String?): BaseResponse<Patient> {
        if(patientId==null){
            return BaseResponse.ErrorResponse(message = "Nisu uneti ispravni podaci za pronalazenje pacijenta")

        }
        val patient=service.getPatientById(patientId)
        if(patient==null)
            return BaseResponse.ErrorResponse(message = "Pacijent nije pronadjen")
        return BaseResponse.SuccessResponse(data = patient,"Pacijent uspesno pronadjen")
    }

    override suspend fun getPatientByJmbg(jmbg: String?): BaseResponse<Patient> {
        if(jmbg==null){
            return BaseResponse.ErrorResponse(message = "Nisu uneti ispravni podaci za pronalazenje pacijenta")

        }
        val patient=service.getPatientByJmbg(jmbg)
        if(patient==null)
            return BaseResponse.ErrorResponse(message = "Pacijent nije pronadjen")
        return BaseResponse.SuccessResponse(data = patient,"Pacijent uspesno pronadjen")
    }

    override suspend fun getAllPatientInHospital(hospitalId: String?): ListResponse<Patient> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Prosledili ste pogresne podatke o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null)
            return ListResponse.ErrorResponse(message = "Bolnica koju vi trazite ne postoji")
        val patients=service.getAllPatientInHospital(hospitalId)
        if (patients.isEmpty()){
            return ListResponse.ErrorResponse(message = "Bolnica nema pacijente")
        }
        return ListResponse.SuccessResponse(data = patients, message = "Pacijenti u bolnici uspesno pronadjeni")


    }

    override suspend fun editPatient(patient: Patient?): BaseResponse<Patient> {
        if (patient==null){
            return BaseResponse.ErrorResponse(message = "Ne mozete izmeniti pacijenta koji nema vrednost")
        }
        val patientEdited=service.editPatient(patient)
        if (patientEdited==null||patientEdited.equals(patient)){
            return BaseResponse.ErrorResponse(message = "Pacijent nije uspesno izmenjen")
        }
        return BaseResponse.SuccessResponse(data = patientEdited, message = "Pacijent uspesno izmenjen")
    }

    override suspend fun deletePatient(patientId: String?): BaseResponse<Boolean> {
        if (patientId==null){
            return BaseResponse.ErrorResponse(message = "Niste prosledili parametre za pacijenta kog zelite da obrisete")
        }
        val patientDeleted=service.deletePatient(patientId)
        if (!patientDeleted){
            return BaseResponse.ErrorResponse(message = "Pacijent nije uspesno obrisan")

        }
        return BaseResponse.SuccessResponse(data = true, message = "Pacijent uspesno obrisan")
    }
}