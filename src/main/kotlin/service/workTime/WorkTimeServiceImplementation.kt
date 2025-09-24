package com.example.service.workTime

import com.example.domain.WorkTime
import com.example.service.doctor.DoctorServiceImplementation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Klasa koja implementira [WorkTimeInterface]
 * @author Lazar Janković
 * @see WorkTimeInterface
 * @property file Json fajl koji simulira kolonu work_time u bazi
 * @property file2 Json fajl koji simulira kolonu doctors u bazi
 * @property list Lista u koju se citaju podaci iz [file]
 * @property doctors Lista u koju se citaju podaci iz [file2]
 */
class WorkTimeServiceImplementation: WorkTimeInterface {
    val file= File("workTime.json")
    val file2= File("doctors.json")
    private  var list: MutableList<WorkTime>
    init {
         list=getAllWorkTimes()

    }
    val doctors= DoctorServiceImplementation().getAllDoctors(file2)

    /**
     * Implementacija funkcije getWorkTimeForDoctor
     */
    override suspend fun getWorkTimeForDoctor(doctorId: String?): List<WorkTime>? {
        if(doctorId==null)
            throw NullPointerException("Id lekara cije se radno vreme trazi ne moze biti null")
        if (doctorId.isEmpty())
            throw NullPointerException("Id lekara cije se radno vreme trazi ne moze biti prazan string")
        val doctorIdExist=doctors.find { it.getId()==doctorId }
        if (doctorIdExist==null){
            return null
        }
        return list.filter{ it.getDoctorId()==doctorId }
    }
    /**
     * Implementacija funkcije addWorkTime
     */
    override suspend fun addWorkTime(workTime: WorkTime?): WorkTime {
        if (workTime==null)
            throw NullPointerException("Work time ne sme biti null")
        if (list.contains(workTime))
            throw IllegalArgumentException("Radno vreme sa ovim id vec postoji")
        list.add(workTime)
        val jsonString=Json { prettyPrint=true}.encodeToString(list)
        file.writeText(jsonString)
        return workTime

    }
    /**
     * Implementacija funkcije deleteWorkTime
     */
    override suspend fun deleteWorkingTime(id: String?): Boolean {
        println(id)
        println(list)

        if(id==null)
            throw NullPointerException("Id radnog vremena ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id radnog vremena ne sme biti prazan")

        val idContains=list.find{ it.getId()==id }
        println(idContains)

        if (idContains==null)
            return false

        list.remove(idContains)
        file.writeText(Json { prettyPrint = true }.encodeToString(list))
        return true


    }

    /**
     * Implementacija funkcije updateWorkingTime
     */
    override suspend fun updateWorkingTime(workTime: WorkTime?): WorkTime? {
        if (workTime==null)
            throw NullPointerException("Work time ne sme biti null")
        if (!list.contains(workTime))
            throw IllegalArgumentException("Radno vreme sa ovim id ne postoji")

        var idContains=list.find{ it.getId()==workTime.getId() }
        println(idContains)

        if (idContains==null)
            return null

        list.add(workTime)

        list.remove(idContains)

        file.writeText(Json { prettyPrint = true }.encodeToString(list))
        return workTime

    }

    /**
     * Funkcija koja ucitava podatke iz json fajla i prevodu u listu radnih vremena
     */
    fun getAllWorkTimes(): MutableList<WorkTime>{
        if (!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<WorkTime>>(jsonString)
    }

}