package com.example.service.selectedDoctor

import com.example.database.DatabaseFactory
import com.example.database.DoctorTable
import com.example.database.PatientTable
import com.example.database.SelectedDoctorTable
import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.service.DoctorServiceInterface
import com.example.service.patient.PatientServiceInterface
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira metode za rukovanje podacima o izabranim lekarima
 *
 * @author Lazar Janković
 *
 * @param doctorServiceInterface Ovde se nalaze metode za rukovanje podacima o lekaru
 * @param patientService Ovde se nalaze metode za rukovanje podacima o pacijentima

 * @see DoctorServiceInterface
 * @see PatientServiceInterface
 *
 */
class SelectedDoctorServiceImplementation(val doctorServiceInterface: DoctorServiceInterface, val patientService: PatientServiceInterface): SelectedDoctorServiceInterface {


    /**
     * Dodavanje izabranog lekara pacijentu i povecavnje [Doctor.currentPatients]
     */
    override suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): SelectedDoctor? {
        if (selectedDoctor==null)
            throw NullPointerException("Podaci o izabranom lekaru ne smeju biti null")

        return DatabaseFactory
            .dbQuery {
                SelectedDoctorTable.insertReturning {
                    it[patientId]= UUID.fromString(selectedDoctor.getPatientId())
                    it[doctorId]= UUID.fromString(selectedDoctor.getDoctorId())
                }.map{
                    rowToSelDoctor(it)
                }.firstOrNull()
            }
    }

    /**
     * Vracanje liste lekara za jednog pacijenta, dohvatanjem jednog po jednog lekara preko [doctorServiceInterface]
     */
    override suspend fun getSelectedDoctorsForPatients(patientId: String?): List<Doctor> {
        if (patientId == null)
            throw NullPointerException("Id pacijenta za koga se traze izabrani lekari ne sme biti null")
        if (patientId.isEmpty()) {
            throw IllegalArgumentException("Id pacijenta za koga se traze izabrani lekari ne moze biti prazan")
        }
         val sel=DatabaseFactory
            .dbQuery {
                SelectedDoctorTable.selectAll().where {
                    SelectedDoctorTable.patientId eq UUID.fromString(patientId)
                }.mapNotNull {
                    rowToSelDoctor(it)
                }
            }
       val list= sel.map { sel->
            DatabaseFactory.dbQuery {
                DoctorTable.selectAll()
                    .where{
                        DoctorTable.id eq UUID.fromString(sel.getDoctorId())
                    }.mapNotNull { rowToDoctor(it) }.first()
            }
        }
        return list

    }

    /**
     * Vracanje liste pacijenata kojima je lekar izabrani pomocu metode iz [patientService]
     */
    override suspend fun getPatientsForSelectedDoctor(doctorId: String?): List<Patient> {
        if (doctorId==null)
            throw NullPointerException("Id lekara za koga se traze pacijenti ne sme biti null")
        if(doctorId.isEmpty()){
            throw IllegalArgumentException("Id lekara za koga se traze pacijenti ne moze biti prazan")
        }
        val sel=DatabaseFactory
            .dbQuery {
                SelectedDoctorTable.selectAll().where {
                    SelectedDoctorTable.doctorId eq UUID.fromString(doctorId)
                }.mapNotNull {
                    rowToSelDoctor(it)
                }
            }
        val list= sel.map { it->
            DatabaseFactory.dbQuery {
                PatientTable.selectAll()
                    .where{
                        PatientTable.id eq UUID.fromString(it.getPatientId())
                    }.mapNotNull { rowToPatient(it) }.first()
            }
        }
        return list

    }

    /**
     * Azuriranje izabranog lekara
     */
    override suspend fun editSelectedDoctor(selectedDoctor: SelectedDoctor?): SelectedDoctor? {
        if (selectedDoctor==null)
            throw NullPointerException("Ne mozete menjati prazne podatke")
        return DatabaseFactory.dbQuery {
            SelectedDoctorTable.updateReturning(where = { SelectedDoctorTable.patientId eq UUID.fromString(selectedDoctor.getPatientId())}){
                it[doctorId]= UUID.fromString(selectedDoctor.getDoctorId())
            }.map{
                rowToSelDoctor(it)
            }.firstOrNull()
        }
    }

    /**
     * Brisanje podataka iz baze
     */
    override suspend fun deleteSelectedDoctor(selectedDoctor: SelectedDoctor?): Boolean {

        val deleted=DatabaseFactory.dbQuery {
            SelectedDoctorTable.deleteWhere {
                SelectedDoctorTable.patientId eq UUID.fromString(selectedDoctor?.getPatientId()) and
                        (SelectedDoctorTable.doctorId eq UUID.fromString(selectedDoctor?.getDoctorId()))
            }
        }
        return deleted>0
    }

    /**
     * Dohvatanje svih podataka iz tabele selected_doctor
     */
    override suspend fun getAllSelectedDoctors(): List<SelectedDoctor>{

        return DatabaseFactory.dbQuery {
            SelectedDoctorTable.selectAll()
                .mapNotNull {
                    rowToSelDoctor(it)
                }
        }
    }
    fun rowToSelDoctor(resultRow: ResultRow?): SelectedDoctor?{
        return if (resultRow==null)
            null
        else{
            SelectedDoctor(
                patientId = resultRow[SelectedDoctorTable.patientId].toString(),
                doctorId = resultRow[SelectedDoctorTable.doctorId].toString()
            )
        }
    }
    fun rowToDoctor(resultRow: ResultRow?): Doctor?{
        if(resultRow==null)
            throw NullPointerException("Red u tabeli ne moze biti null")
        return Doctor(
            id = resultRow[DoctorTable.id].toString(),
            userId = resultRow[DoctorTable.userId].toString(),
            fullName = resultRow[DoctorTable.fullName],
            specialization = resultRow[DoctorTable.specialization],
            maxPatients = resultRow[DoctorTable.maxPatients]!!,
            currentPatients = resultRow[DoctorTable.currentPatients],
            hospitalId = resultRow[DoctorTable.hospitalId].toString(),
            isGeneral = resultRow[DoctorTable.isGeneral]
        )
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