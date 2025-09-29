package com.example.service.hospital

import com.example.database.DatabaseFactory
import com.example.database.HospitalTable
import com.example.domain.Hospital
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.io.File
import java.util.UUID

/**
 * Klasa koja implementira metode [HospitalServiceInterface]
 * @author Lazar Janković
 * @see HospitalServiceInterface
 * @property file Json fajl koji simulira kolonu u bazi
 * @property list Lista bolnica koja se ucitava iz i u json fajl
 *
 */
class HospitalServiceImplementation: HospitalServiceInterface {



    /**
     * Dodavanje nove bolnice
     */
    override suspend fun addHospital(hospital: Hospital?): Hospital? {
        val list=getAllHospitals()
        if (hospital==null)
            throw NullPointerException("Bolnica ne moze biti null")
        if (list.contains(hospital))
            throw IllegalArgumentException("Bolnica vec postoji")

        val id=DatabaseFactory.dbQuery {
            HospitalTable.insert {
                it[name]=hospital.getName()
                it[city]=hospital.getCity()
                it[address]=hospital.getCity()
            }[HospitalTable.id]
        }
        return DatabaseFactory.dbQuery {
            HospitalTable.select(HospitalTable.id eq id).firstNotNullOfOrNull {
                rowToHospital(it)
            }
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