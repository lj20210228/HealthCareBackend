package com.example.service.doctor

import com.example.database.DatabaseFactory
import com.example.database.DoctorTable
import com.example.domain.Doctor
import com.example.service.DoctorServiceInterface
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.updateReturning
import java.io.File
import java.util.UUID

/**
 * Klasa koja sluzi za implementaciju [DoctorServiceInterface] interfejsa

 * @author Lazar Janković
 * @see DoctorServiceInterface
 */
class DoctorServiceImplementation: DoctorServiceInterface {





    /**
     * Dodaje novog lekara
     * @see DoctorServiceInterface
     * @see Doctor
     */
    override suspend fun addDoctor(doctor: Doctor?): Doctor? {
        if (doctor==null)
            throw NullPointerException("Nisu ispravni prosledjeni podaci o lekaru")



       return DatabaseFactory.dbQuery {
            DoctorTable.insertReturning {
                it[userId]= UUID.fromString(doctor.getUserId())
                it[fullName]=doctor.getFullName()
                it[isGeneral]=doctor.getIsGeneral()
                it[specialization]=doctor.getSpecialization()
                it[currentPatients]=doctor.getCurrentPatients()
                it[maxPatients]=doctor.getMaxPatients()
                it[hospitalId]= UUID.fromString(doctor.getHospitalId())
            }.map{
                rowToDoctor(it)
            }.firstOrNull()
        }





    }

    /**
     * Pronalazi lekara po id
     * @see Doctor
     * @see DoctorServiceInterface
     */
    override suspend fun getDoctorForId(doctorId: String?): Doctor? {
        if(doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")


        return DatabaseFactory.dbQuery {
            DoctorTable.selectAll().where(DoctorTable.id eq UUID.fromString(doctorId))
                .mapNotNull { rowToDoctor(it) }.firstOrNull()

        }


    }

    override suspend fun getDoctorForUserId(userId: String?): Doctor? {
        if(userId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(userId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")


        return DatabaseFactory.dbQuery {
            DoctorTable.selectAll().where(DoctorTable.userId eq UUID.fromString(userId))
                .mapNotNull { rowToDoctor(it) }.firstOrNull()

        }
    }

    /**
     * Pronalazi sve lekare u jednoj bolnici
     * @see Doctor
     * @see DoctorServiceInterface
     */
    override suspend fun getAllDoctorsInHospital(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        return DatabaseFactory.dbQuery {
            DoctorTable.selectAll().where(DoctorTable.hospitalId eq UUID.fromString(hospitalId))
                .mapNotNull { rowToDoctor(it) }

        }


    }
    /**
     * Pronalazi sve lekare opste prakse u jednoj bolnici koji imaju mesta da budu izabrani
     * @see DoctorServiceInterface
     * @see Doctor
     */
    override suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")

        return DatabaseFactory
            .dbQuery {
                DoctorTable.selectAll().where {
                    DoctorTable.hospitalId eq UUID.fromString(hospitalId) and
                            ( DoctorTable.currentPatients less  DoctorTable.maxPatients)

                }.mapNotNull {
                    rowToDoctor(it)
                }
            }
    }

    /**
     * Pronalazi sve lekare opste prakse u jednoj bolnici
     * @see DoctorServiceInterface
     * @see Doctor
     */
    override suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        return DatabaseFactory
            .dbQuery {
                DoctorTable.selectAll().where {
                    DoctorTable.hospitalId eq UUID.fromString(hospitalId) and
                            ( DoctorTable.isGeneral eq true)

                }.mapNotNull {
                    rowToDoctor(it)
                }
            }
    }

    /**
     *
     * Pronalazi sve lekare odredjene specijalizacije u jednoj bolnici
     * @see DoctorServiceInterface
     * @see Doctor
     *
     */
    override suspend fun getAllDoctorInHospitalForSpecialization(
        hospitalId: String?,
        specialization: String?
    ): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        if (specialization==null)
            throw NullPointerException("Specijalizacije lekara ne sme biti null")
        if(specialization.isEmpty())
            throw IllegalArgumentException("Specijalizacija lekara ne sme biti prazan string")

        return DatabaseFactory
            .dbQuery {
                DoctorTable.selectAll().where {
                    DoctorTable.hospitalId eq UUID.fromString(hospitalId) and
                    (DoctorTable.isGeneral eq true) and
                            ( DoctorTable.specialization eq specialization)
                }.mapNotNull {
                    rowToDoctor(it)
                }
            }
    }
    /**
     *
     * Pronalazi sve lekare odredjene specijalizacije u jednoj bolnici, koji imaju mesta da budu izabrani
     * @see DoctorServiceInterface
     * @see Doctor
     *
     */
    override suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId: String?,specialization: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        if (specialization==null)
            throw NullPointerException("Specijalizacije lekara ne sme biti null")
        if(specialization.isEmpty())
            throw IllegalArgumentException("Specijalizacija lekara ne sme biti prazan string")

        return DatabaseFactory
            .dbQuery {
                DoctorTable.selectAll().where {
                    DoctorTable.hospitalId eq UUID.fromString(hospitalId) and
                            ( DoctorTable.currentPatients less  DoctorTable.maxPatients) and
                            (DoctorTable.specialization eq specialization)

                }.mapNotNull {
                    rowToDoctor(it)
                }
            }
    }

    /**
     * Povecava broj trenutnih pacijenata za 1
     * @see Doctor
     */
    override suspend fun editCurrentPatients(doctorId: String?): Boolean {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")

        return DatabaseFactory.dbQuery {
            val doctor = DoctorTable
                .selectAll()
                .where{
                    DoctorTable.id eq UUID.fromString(doctorId)
                }
                .singleOrNull()

            if (doctor == null) return@dbQuery false

            val current = doctor[DoctorTable.currentPatients]
            val max = doctor[DoctorTable.maxPatients]
            if (current >= max!!) return@dbQuery false
            val updated = DoctorTable.update({ DoctorTable.id eq UUID.fromString(doctorId) }) {
                it[DoctorTable.currentPatients] = current + 1
            }

            updated > 0
        }


    }
    /**
     * Funkcija koja ucitava listu svih lekara u JSON fajlu
     * @return [MutableList[Doctor]] Lista svih lekara
     */
     override suspend fun getAllDoctors(): List<Doctor>{
         return DatabaseFactory.dbQuery {
             DoctorTable.selectAll()
                 .mapNotNull { rowToDoctor(it) }
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
}