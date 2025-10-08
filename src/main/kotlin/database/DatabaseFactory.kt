package com.example.database

import com.example.domain.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * DatabaseFactory je centralno mesto za inicijalizaciju baze podataka
 * i Exposed ORM integraciju.
 *
 * Ovaj objekat:
 * - uspostavlja konekciju sa Postgres bazom,
 * - kreira tabele pomoću Exposed SchemaUtils.create,
 * - obezbeđuje dbQuery helper funkciju za asinhrono izvršavanje upita.
 *
 * Koristi se pri startovanju Ktor servera kako bi se baza inicijalizovala.
 */
object DatabaseFactory {
    /**
     * Inicijalizuje konekciju ka bazi i kreira sve potrebne tabele.
     *
     * Tabele koje se kreiraju:
     * -UserTable
     * -DoctorTable
     * -PatientTable
     * -HospitalTable
     * -SelectedDoctor
     *
     * Funkcija se poziva pri pokretanju aplikacije.
     */

    fun init() {

        connectToDb()

        transaction {
            SchemaUtils.create(UserTable)
            println("Kreirana baza")
        }
        transaction {
            SchemaUtils.create(HospitalTable)

        }
        transaction {
            SchemaUtils.create(DoctorTable)
        }
        transaction {
            SchemaUtils.create(PatientTable)
        }
        transaction {
            SchemaUtils.create(SelectedDoctorTable)
        }
        transaction {
            SchemaUtils.create(RecipeTable)
        }
        transaction {
            SchemaUtils.create(TerminTable)
        }
        transaction {
            SchemaUtils.create(WorkTimeTable)
        }
        transaction {
            SchemaUtils.create(ChatTable)
        }

    }






    /**
     * @see Database.connect koristi se za inicijalizaciju konekcije
     *
     * @property url JDBC URL konekcije ka Postgres baz
     * @property driver  drajver za Postgres ("org.postgresql.Driver").
     * @property user Korisničko ime za pristup bazi
     * @property password Lozinka za korisnika baze
     */

    fun connectToDb(){
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/health_care",
            driver = "org.postgresql.Driver",
            user = "myuser",
            password = "myuser"
        )
    }
    /**
     *Univerzalna f-ja za brisanje svih podataka iz tabele
     * koristice se u testovima
     *
     */
    fun clearTable(tableName: String) {
        transaction {
            exec("TRUNCATE TABLE $tableName RESTART IDENTITY CASCADE")
        }
    }
    /**
     *
     *Pokreće Exposed transakciju u okviru IO dispatcher-a
     *
     * Ova funckija se koristi da bi si svi upiti ka bazi izvršavali
     * asinhrono i u posebnom thread pool-u (Dispatchers.IO)
     * čime se izbegava blokiranje glavnog thread-a Ktor servera
     *
     * @param block Lambda blok sa SQL upitima
     * @return Rezultat izvršavanja upita
     */
    suspend fun <T>dbQuery(block:()->T):T= withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }
}