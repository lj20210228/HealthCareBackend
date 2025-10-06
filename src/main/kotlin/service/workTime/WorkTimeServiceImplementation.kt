package com.example.service.workTime

import com.example.database.DatabaseFactory
import com.example.database.WorkTimeTable
import com.example.domain.WorkTime
import com.example.service.doctor.DoctorServiceImplementation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira [WorkTimeInterface]
 * @author Lazar Janković
 * @see WorkTimeInterface
 */
class WorkTimeServiceImplementation: WorkTimeInterface {





    /**
     * Implementacija funkcije getWorkTimeForDoctor
     */
    override suspend fun getWorkTimeForDoctor(doctorId: String?): List<WorkTime> {
        if(doctorId==null)
            throw NullPointerException("Id lekara cije se radno vreme trazi ne moze biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara cije se radno vreme trazi ne moze biti prazan string")

        return DatabaseFactory
            .dbQuery {
                WorkTimeTable.selectAll().where {
                    WorkTimeTable.doctorId eq UUID.fromString(doctorId)
                }.mapNotNull {
                    rowToWorkTime(it)
                }
            }
    }
    /**
     * Implementacija funkcije addWorkTime
     */
    override suspend fun addWorkTime(workTime: WorkTime?): WorkTime? {
        if (workTime==null)
            throw NullPointerException("Work time ne sme biti null")


        return DatabaseFactory.dbQuery {
            WorkTimeTable.insertReturning {
                it[startTime]=workTime.getStartTime()
                it[endTime]=workTime.getEndTime()
                it[doctorId]= UUID.fromString(workTime.getDoctorId())
                it[dayInWeek]=workTime.getDayIn()
            }.map {
                rowToWorkTime(it)
            }.first()
        }

    }
    /**
     * Implementacija funkcije deleteWorkTime
     */
    override suspend fun deleteWorkingTime(id: String?): Boolean {


        if(id==null)
            throw NullPointerException("Id radnog vremena ne sme biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id radnog vremena ne sme biti prazan")


        val deletedRows= DatabaseFactory
            .dbQuery {
                WorkTimeTable.deleteWhere {
                    WorkTimeTable.id eq UUID.fromString(id)
                }
            }

        return deletedRows>0

    }

    /**
     * Implementacija funkcije updateWorkingTime
     */
    override suspend fun updateWorkingTime(workTime: WorkTime?): WorkTime? {
        if (workTime==null)
            throw NullPointerException("Work time ne sme biti null")
        return DatabaseFactory
            .dbQuery {
                WorkTimeTable.updateReturning(where = {WorkTimeTable.id eq UUID.fromString(workTime.getId())})
                {
                    it[startTime]=workTime.getStartTime()
                    it[endTime]=workTime.getEndTime()
                }.map {
                    rowToWorkTime(it)
                }.firstOrNull()
            }

    }

     fun rowToWorkTime(resultRow: ResultRow?): WorkTime?{
        return if (resultRow==null)
            null
        else WorkTime(
            id = resultRow[WorkTimeTable.id].toString(),
            startTime = resultRow[WorkTimeTable.startTime],
            endTime = resultRow[WorkTimeTable.endTime],
            doctorId = resultRow[WorkTimeTable.doctorId].toString(),
            dayIn = resultRow[WorkTimeTable.dayInWeek]
        )
    }
    override suspend fun getAllWorkingTimes(): List<WorkTime> {
        return DatabaseFactory.dbQuery {
            WorkTimeTable.selectAll().mapNotNull {
                rowToWorkTime(it)
            }
        }
    }


}