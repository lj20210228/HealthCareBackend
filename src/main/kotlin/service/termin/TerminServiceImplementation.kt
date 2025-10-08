package com.example.service.termin

import com.example.database.DatabaseFactory
import com.example.database.TerminTable
import com.example.domain.Termin
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import java.time.LocalDate
import java.util.UUID

/**
 * Klasa koja implementira interfejs [TerminServiceInterface]
 * @see TerminServiceInterface
 * @see Termin
 * @author Lazar Jankovic
 */
class TerminServiceImplementation: TerminServiceInterface {
    override suspend fun addTermin(termin: Termin?): Termin? {
        if (termin==null)
            throw NullPointerException("Termin koji dodajete ne moze biti null")

        return DatabaseFactory
            .dbQuery {
                TerminTable.insertReturning {
                    it[doctorId]= UUID.fromString(termin.getDoctorId())
                    it[patientId]= UUID.fromString(termin.getPatientId())
                    it[date]=termin.getDate()
                    it[startTime]=termin.getStartTime()
                    it[endTime]=termin.getEndTime()
                    it[hospitalId]= UUID.fromString(termin.getHospitalId())
                    it[status]=termin.getTerminStatus()
                }.map {
                    rowToTermin(it)
                }.firstOrNull()
            }
    }

    override suspend fun editTermin(termin: Termin?): Termin? {
        if (termin==null)
            throw NullPointerException("Termin koji zelite da izmenite ne moze biti null")
        return DatabaseFactory
            .dbQuery {
                TerminTable.updateReturning{
                    it[startTime]=termin.getStartTime()
                    it[endTime]=termin.getEndTime()
                    it[date]=termin.getDate()
                    it[doctorId]= UUID.fromString(termin.getDoctorId())
                    it[status]=termin.getTerminStatus()
                }.map {
                    rowToTermin(it)
                }.firstOrNull()
            }
    }

    override suspend fun deleteTermin(terminId: String?): Boolean {
        if (terminId==null)
            throw NullPointerException("Id termina koji zelite da obrisete ne moze biti null")
        if (terminId.isEmpty())
            throw IllegalArgumentException("Id termina koji zelite da obrisete ne moze biti prazan")

        return DatabaseFactory.dbQuery {
            TerminTable.deleteWhere {
                TerminTable.id eq UUID.fromString(terminId)
            }
        }>0
    }

    override suspend fun getTerminForId(terminId: String?): Termin? {
        if (terminId==null)
            throw NullPointerException("Id termina koji trazite ne moze biti null")
        if (terminId.isEmpty())
            throw IllegalArgumentException("Id termina koji zelite da trazite ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .where {
                    TerminTable.id eq UUID.fromString(terminId)
                }.map { rowToTermin(it) }.firstOrNull()
        }

    }

    override suspend fun getTerminsForDoctor(doctorId: String?): List<Termin> {
        if (doctorId==null)
            throw NullPointerException("Id lekara cije termine trazite ne moze biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara cije termine trazite ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .where {
                    TerminTable.doctorId eq UUID.fromString(doctorId)
                }.mapNotNull { rowToTermin(it) }
        }
    }

    override suspend fun getTerminsForPatient(patientId: String?): List<Termin> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta cije termine trazite ne moze biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta cije termine trazite ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .where {
                    TerminTable.patientId eq UUID.fromString(patientId)
                }.mapNotNull { rowToTermin(it) }
        }
    }

    override suspend fun getTerminsForDoctorForDate(
        doctorId: String?,
        date: LocalDate
    ): List<Termin> {
        if (doctorId==null)
            throw NullPointerException("Id lekara cije termine trazite ne moze biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara cije termine trazite ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .where {
                    TerminTable.doctorId eq UUID.fromString(doctorId) and
                            (TerminTable.date eq date)
                }.mapNotNull { rowToTermin(it) }
        }
    }

    override suspend fun getTerminsForPatientForDate(
        patientId: String?,
        date: LocalDate
    ): List<Termin> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta cije termine trazite ne moze biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta cije termine trazite ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .where {
                    TerminTable.patientId eq UUID.fromString(patientId) and
                            (TerminTable.date eq date)
                }.mapNotNull { rowToTermin(it) }
        }
    }

    override suspend fun getAllTermins(): List<Termin> {
        return DatabaseFactory.dbQuery {
            TerminTable.selectAll()
                .mapNotNull { rowToTermin(it) }
        }
    }

    fun rowToTermin(resultRow: ResultRow?): Termin?
    {
        if (resultRow==null)
            return null
        else return Termin(
            id = resultRow[TerminTable.id].toString(),
            doctorId = resultRow[TerminTable.doctorId].toString(),
            patientId = resultRow[TerminTable.patientId].toString(),
            date = resultRow[TerminTable.date],
            startTime = resultRow[TerminTable.startTime],
            endTime = resultRow[TerminTable.endTime],
            hospitalId = resultRow[TerminTable.hospitalId].toString(),
            status = resultRow[TerminTable.status]
        )
    }
}