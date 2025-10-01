package com.example.repository.doctor

import com.example.domain.Doctor
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.hospital.HospitalServiceInterface
import com.fasterxml.jackson.databind.ser.Serializers
import javax.print.Doc

/**
 * Klasa koja implemtira metode [DoctorRepository]
 * @author Lazar Janković
 * @see Doctor
 * @see DoctorRepository
 *
 */
class DoctorRepositoryImplementation(val service: DoctorServiceInterface,
    val hospitalService: HospitalServiceInterface): DoctorRepository {
    override suspend fun addDoctor(doctor: Doctor?): BaseResponse<Doctor> {
        if (doctor==null){
            return BaseResponse.ErrorResponse(message = "Niste uneli ispravne podatke o lekaru")
        }

        val doctor=service.addDoctor(doctor)
        if (doctor==null){
            BaseResponse.ErrorResponse<Doctor>(message = "Lekar nije uspesno dodat u bazu")
        }
        return BaseResponse.SuccessResponse(data = doctor,"Lekar je uspesno dodat u bazu")

    }

    override suspend fun getDoctorForId(doctorId: String?): BaseResponse<Doctor> {
        if (doctorId==null){
            return BaseResponse.ErrorResponse(message = "Niste uneli ispravne podatke o lekaru")
        }
        val doctor=service.getDoctorForId(doctorId)
        if (doctor==null){
            BaseResponse.ErrorResponse<Doctor>(message = "Lekar sa tim id ne postoji")
        }
        return BaseResponse.SuccessResponse(data = doctor,"Lekar je uspesno pronadjen")
    }

    override suspend fun getAllDoctorsInHospital(hospitalId: String?): ListResponse<Doctor> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null){
            return ListResponse.ErrorResponse(message = "Bolnica ne postoji")
        }
        val doctors=service.getAllDoctorsInHospital(hospitalId)
        if(doctors.isEmpty()){
            return ListResponse.ErrorResponse(message = "Nema lekara u ovoj bolnci")
        }
        return ListResponse.SuccessResponse(data = doctors,"Lekari su uspesno pronadjeni")
    }

    override suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): ListResponse<Doctor> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null){
            return ListResponse.ErrorResponse(message = "Bolnica ne postoji")
        }
        val doctors=service.getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId)
        if(doctors.isEmpty()){
            return ListResponse.ErrorResponse(message = "Nema lekara opste prakse koji mogu da budu izabrani u ovoj bolnci")
        }
        return ListResponse.SuccessResponse(data = doctors,"Lekari opste prakse koji mogu da budu izabrani su uspesno pronadjeni")
    }

    override suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): ListResponse<Doctor> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null){
            return ListResponse.ErrorResponse(message = "Bolnica ne postoji")
        }
        val doctors=service.getAllGeneralDoctorsInHospital(hospitalId)
        if(doctors.isEmpty()){
            return ListResponse.ErrorResponse(message = "Nema lekara opste prakse u ovoj bolnci")
        }
        return ListResponse.SuccessResponse(data = doctors,"Lekari opste prakse su uspesno pronadjeni")
    }

    override suspend fun getAllDoctorInHospitalForSpecialization(
        hospitalId: String?,
        specialization: String?
    ): ListResponse<Doctor> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null){
            return ListResponse.ErrorResponse(message = "Bolnica ne postoji")
        }
        val doctors=service.getAllDoctorInHospitalForSpecialization(hospitalId,specialization)
        if(doctors.isEmpty()){
            return ListResponse.ErrorResponse(message = "Nema lekara ove specijalizacije u ovoj bolnci")
        }
        return ListResponse.SuccessResponse(data = doctors,"Lekari specijalizacije $specialization su uspesno pronadjeni")
    }

    override suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(
        hospitalId: String?,
        specialization: String?
    ): ListResponse<Doctor> {
        if (hospitalId==null){
            return ListResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o bolnici")
        }
        val hospital=hospitalService.getHospitalById(hospitalId)
        if (hospital==null){
            return ListResponse.ErrorResponse(message = "Bolnica ne postoji")
        }
        val doctors=service.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId,specialization)
        if(doctors.isEmpty()){
            return ListResponse.ErrorResponse(message = "Nema lekara ove specijalizacije koji mogu da budu izabrani u ovoj bolnci")
        }
        return ListResponse.SuccessResponse(data = doctors,"Lekari specijalizacije $specialization koji mogu da budu izabrani su uspesno pronadjeni")
    }

    override suspend fun editCurrentPatients(doctorId: String?): BaseResponse<Boolean> {
        if (doctorId==null){
            return BaseResponse.ErrorResponse(message = "Nisu uneti ispravni podaci o lekaru")
        }

        val doctor=service.editCurrentPatients(doctorId)

        if (!doctor)
            return BaseResponse.ErrorResponse(message = "Nisu uspesno azuzirani podaci o trenutnom broju pacijenata")
        return BaseResponse.SuccessResponse(data = doctor,"Broj pacijenata je uspesno azuriran")
    }

}