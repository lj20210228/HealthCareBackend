package com.example.service.hospital

import com.example.database.DatabaseFactory
import com.example.database.HospitalTable
import com.example.domain.Hospital
import com.example.request.HospitalRequest
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.Statement
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira metode [HospitalServiceInterface]
 * @author Lazar Janković
 * @see HospitalServiceInterface
 *
 */
class HospitalServiceImplementation: HospitalServiceInterface {



    /**
     * Dodavanje nove bolnice
     */
    override suspend fun addHospital(hospital: HospitalRequest?): Hospital? {


        if (hospital==null)
            throw NullPointerException("Bolnica ne moze biti null")

         return DatabaseFactory.dbQuery {
             HospitalTable.insertReturning {
                it[name]=hospital.name
                it[city]=hospital.city
                it[address]=hospital.address
            }.map {
                rowToHospital(it)
             }.firstOrNull()

        }

    }

    /**
     * Trazenje bolnice po id
     */
    override suspend fun getHospitalById(hospitalId: String?): Hospital? {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne moze biti null")
        if (hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne moze biti prazan string")
        return DatabaseFactory.dbQuery {
            HospitalTable.select(HospitalTable.id eq UUID.fromString(hospitalId))
                .firstNotNullOfOrNull {
                    rowToHospital(it)
                }
        }

    }
    /**
     * Trazenje bolnice po imenu
     */
    override suspend fun getHospitalByName(name: String?): Hospital? {
        if (name==null)
            throw NullPointerException("Ime bolnice ne moze biti null")
        if (name.isEmpty())
            throw IllegalArgumentException("Ime bolnice ne moze biti prazan string")
        return DatabaseFactory.dbQuery {
            HospitalTable.select(HospitalTable.name eq (name))
                .firstNotNullOfOrNull {
                    rowToHospital(it)
                }
        }


    }
    /**
     * Trazenje svih bolnica u gradu
     */
    override suspend fun getHospitalsInCity(city: String?): List<Hospital> {
        if (city==null)
            throw NullPointerException("Ime grada ne moze biti null")
        if (city.isEmpty())
            throw IllegalArgumentException("Ime grada ne moze biti prazan string")
        return DatabaseFactory
            .dbQuery {
                HospitalTable.select(HospitalTable.city eq city)
                    .mapNotNull {
                        rowToHospital(it)
                    }
            }
    }

    /**
     * Pronalazak svih bolnica
     */
    override suspend fun getAllHospitals(): List<Hospital> {
        return getHospitals()
    }

    /**
     * Metoda za dohvatanje svih podataka iz baze
     */
   suspend fun getHospitals(): List<Hospital>{
        return DatabaseFactory.dbQuery {
            HospitalTable.selectAll()
                .mapNotNull {
                    rowToHospital(it)
                }
        }
    }
    fun rowToHospital(resultRow: ResultRow?): Hospital?{
        if(resultRow==null)
            throw NullPointerException("Red iz tabele bolnica je null")
        return Hospital(
            id = resultRow[HospitalTable.id].toString(),
            name = resultRow[HospitalTable.name],
            city = resultRow[HospitalTable.city],
            address = resultRow[HospitalTable.address]
        )
    }
}