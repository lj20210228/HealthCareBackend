package com.example.repository.hospital

import com.example.domain.Hospital
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.hospital.HospitalServiceInterface
import io.ktor.server.http.content.resourceClasspathResource

class HospitalRepositoryImplementation(val service: HospitalServiceInterface): HospitalRepository {
    override suspend fun addHospital(hospital: Hospital?): BaseResponse<Hospital> {
        if (hospital==null)
            return BaseResponse.ErrorResponse(message = "Nisu prosledjeni podaci o bolnici")
        val list=service.getAllHospitals()
        if(list.contains(hospital))
            return BaseResponse.ErrorResponse(message = "Bolnica vec postoji")
        val hospitalAdded=service.addHospital(hospital)
        if (hospitalAdded==null)
            return BaseResponse.ErrorResponse(message = "Bolnica nije uspesno dodata")
        return BaseResponse.SuccessResponse(data = hospitalAdded, message = "Bolnica uspesno dodata")
    }

    override suspend fun getHospitalById(hospitalId: String?): BaseResponse<Hospital> {
        if (hospitalId==null)
            return BaseResponse.ErrorResponse(message = "Nije prosledjen ispravan argument za pretragu bolnice")
        val hospital=service.getHospitalById(hospitalId)
        if(hospital==null)
            return BaseResponse.ErrorResponse(message = "Bolnica sa ovim podacima ne postoji")
        return BaseResponse.SuccessResponse(data = hospital,"Bolnica pronadjena")
    }

    override suspend fun getHospitalByName(name: String?): BaseResponse<Hospital> {
        if (name==null)
            return BaseResponse.ErrorResponse(message = "Nije prosledjen ispravan argument za pretragu bolnice")
        val hospital=service.getHospitalByName(name)
        if(hospital==null)
            return BaseResponse.ErrorResponse(message = "Bolnica sa ovim podacima ne postoji")
        return BaseResponse.SuccessResponse(data = hospital,"Bolnica pronadjena")
    }

    override suspend fun getHospitalsInCity(city: String?): ListResponse<Hospital> {
        if (city==null)
            return ListResponse.ErrorResponse(message = "Nije prosledjen ispravan argument za pretragu bolnice")
        val hospitals=service.getHospitalsInCity(city)
        if(hospitals.isEmpty())
            return ListResponse.ErrorResponse(message = "Bolnice u ovom gradu ne postoji")
        return ListResponse.SuccessResponse(data = hospitals,"Bolnice pronadjene")
    }

    override suspend fun getAllHospitals(): ListResponse<Hospital> {
        val hospitals=service.getAllHospitals()
        if(hospitals.isEmpty())
            return ListResponse.ErrorResponse(message = "Bolnice jos ne postoje")
        return ListResponse.SuccessResponse(data = hospitals,"Bolnice pronadjene")

    }
}