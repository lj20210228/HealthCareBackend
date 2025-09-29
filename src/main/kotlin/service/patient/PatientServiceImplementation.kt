package com.example.service.patient

import com.example.database.DatabaseFactory
import com.example.database.PatientTable
import com.example.domain.Patient
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.Statement
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.updateReturning
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira metode interfejsa [PatientServiceInterface]
 * @author Lazar Janković
 * @see PatientServiceInterface
 * @see Patient
 */
class PatientServiceImplementation: PatientServiceInterface {



    /**
     * Funkcija za dodavanje pacijenta u bazu
     */
    override suspend fun addPatient(patient: Patient?): Patient {


        var id: UUID?=null
        if (patient==null)
            throw NullPointerException("Prosledjeni podaci o pacijentu su jednaki null")

        val listOfPatients=getAllPatients()
        if (listOfPatients.contains(patient)){
            throw IllegalArgumentException("Pacijent vec postoji")
        }
         return DatabaseFactory.dbQuery {
            PatientTable.insertReturning{
                it[fullName]=patient.getFullName()
                it[userId]= UUID.fromString(patient.getUserId())
                it[hospitalId]= UUID.fromString(patient.getHospitalId())
                it[jmbg]=patient.getJmbg().toString()
            }.map {
                rowToPatient(it)
            }.first()

         }






    }

    /**
     * Funckija za pronalazenje pacijenta po id
     */
    override suspend fun getPatientById(patientId: String?): Patient? {
        if (patientId==null)
            throw NullPointerException("Prosledjeni patientId jendak je null")
        if(patientId.isEmpty()){
            throw IllegalArgumentException("Id pacijenta ne moze biti null")

        }
        return rowToPatient(DatabaseFactory
            .dbQuery {
                PatientTable.select(PatientTable.id eq UUID.fromString(patientId)).firstOrNull()
            })

    }

    /**
     * Funckija za pronalazenje pacijenta po jmbg
     */
    override suspend fun getPatientByJmbg(jmbg: String?): Patient? {
        if (jmbg==null)
            throw NullPointerException("Prosledjeni jmbg jendak je null")
        if(jmbg.isEmpty()){
            throw IllegalArgumentException("Jmbg ne moze biti null")

        }
        if(!jmbg.all { it.isDigit() }){
            throw IllegalArgumentException("Jmbg mora sadrzati samo brojeve")

        }
        if(jmbg.length!=13){
            throw IllegalArgumentException("Duzina jmbg-a mora biti 13 cifara")

        }
        return rowToPatient(DatabaseFactory
            .dbQuery {
                PatientTable.select(PatientTable.jmbg eq jmbg).firstOrNull()
            })
    }

    /**
     * Funkcija za pronalanje pacijenata iz jedne bolnice
     */
    override suspend fun getAllPatientInHospital(hospitalId: String?): List<Patient> {
        if (hospitalId==null)
            throw NullPointerException("Prosledjeni hospital jendak je null")
        if(hospitalId.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti null")

        }
        return DatabaseFactory.dbQuery {
            PatientTable.selectAll().where(PatientTable.hospitalId eq UUID.fromString(hospitalId))
                .map {
                    rowToPatient(it)
                }
        }
    }

    /**
     * Funckija za izmenu podataka o pacijentu
     */
    override suspend fun editPatient(patient: Patient?): Patient? {


        if (patient==null)
            throw NullPointerException("Pacijent ne moze biti null")
        val list=getAllPatients()
        if (!list.contains(patient))
            throw IllegalArgumentException("Pacijent ne postoji")

         DatabaseFactory.dbQuery {
            PatientTable.update{
                it[fullName]=patient.getFullName()
                it[userId]= UUID.fromString(patient.getUserId())
                it[jmbg]=patient.getJmbg().toString()
                it[hospitalId]= UUID.fromString(patient.getHospitalId())
            }
        }
        return DatabaseFactory.dbQuery {
            PatientTable.select(PatientTable.id eq UUID.fromString(patient.getId()))
                .map { rowToPatient(it) }
                .firstOrNull()
        }

    }
    /**
     * Funckija za brisanje podataka o pacijentu
     */
    override suspend fun deletePatient(patientId: String?): Boolean {
        if (patientId==null)
            throw NullPointerException("Prosledjeni patientId jendak je null")
        if(patientId.isEmpty()) {
            throw IllegalArgumentException("Id pacijenta ne moze biti null")
        }
        val deletedCount= DatabaseFactory.dbQuery {
            PatientTable.deleteWhere {
                PatientTable.id eq UUID.fromString(patientId)

            }

        }
        return deletedCount>0
    }

    /**
     * Funkcija za ucitavanje svih pacijenata iz json fajla
     */
    suspend fun getAllPatients(): List<Patient>{
        return DatabaseFactory.dbQuery {
            PatientTable.selectAll().map {
                rowToPatient(it)
            }
        }

    }
    fun rowToPatient(resultRow: ResultRow?): Patient{
        if(resultRow==null)
            throw NullPointerException("Vraceno je null polje iz baze")
        else return Patient(
            id = resultRow[PatientTable.id].toString(),
            userId = resultRow[PatientTable.userId].toString(),
            fullName = resultRow[PatientTable.fullName],
            hospitalId = resultRow[PatientTable.hospitalId].toString(),
            jmbg = resultRow[PatientTable.jmbg]
        )
    }


}