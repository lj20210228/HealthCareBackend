package com.example.database

import com.example.domain.Role
import com.example.domain.TerminStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time

object UserTable: Table("users")
{
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val email=varchar("email",256)
    val password=text("password")
    val role=enumeration<Role>("role")
}
/**
 * Tabela u kojoj se čuvaju podaci o lekaru
 *
 * @property id Id lekara
 * @property userId Spoljni ključ ka tabeli User
 * @property fullName Ime i prezime lekara
 * @property specialization Specijalizacija lekara
 * @property hospitalId Spoljni ključ ka tabeli HospitalTable, tj Id bolnice kojoj lekar pripada,
 * prilikom brisanja bolnice iz tabele HospitalTable brišu se i podaci iz ove kolone
 *
 * @property maxPatients Maksimalan broj pacijenata kojima može biti izabrani lekar
 * @property currentPatients Trenutni broj pacijenata kojima je izabrani lekar
 * @property isGeneral Da li je lekar opšte prakse
 */
object DoctorTable: Table("doctor"){
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val userId=reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE)
    val fullName=varchar("full_name",256)
    val specialization=varchar("specialization",256)
    val hospitalId=reference("hospital_id", HospitalTable.id, onDelete = ReferenceOption.CASCADE).nullable()
    val maxPatients=integer("max_patients").nullable()
    val currentPatients=integer("current_patients")
    val isGeneral=bool("is_general")

}
/**
 * Tabela gde se nalaze podaci o pacijentima
 *
 * @property id Id pacijenta
 * @property userId Spoljni ključ ka tabeli User
 * @property fullName Ime i prezime pacijenta
 * @property hospitalId Id bolnice , spoljni ključ ka tabeli HospitalTable,
 * gde se pacijent leči ,brisanje isto kao za doktora
 * @property jmbg JMBG pacijenta
 */
object PatientTable: Table("patient"){
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val userId=reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE)

    val fullName=varchar("full_name",256)
    val hospitalId=reference("hospital_id", HospitalTable.id, onDelete = ReferenceOption.CASCADE).nullable()
    val jmbg=varchar("jmbg",256)
}


/**
 * Tabela u kojoj se nalaze podaci o bolnicama
 *
 * @property id Id bolnice
 * @property name Ime bolnice
 * @property address Adresa bolnice
 * @property city Grad u kome se bolnica nalazi
 */
object HospitalTable: Table("hospital"){
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val name=text("name")
    val address=varchar("address",256)
    val city=varchar("city",256)
}

/**
 * Tabela u kojoj se cuvaju podaci o izabranim lekarima za pacijenta
 * @property id Id relacije
 * @property doctorId Id izabranog lekara
 * @property patientId Id pacijenta kome je lekar izabrani
 * @see com.example.domain.SelectedDoctor
 */
object SelectedDoctorTable: Table("selected_doctor"){
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val doctorId=reference("doctor_id", DoctorTable.id, onDelete = ReferenceOption.CASCADE)
    val patientId=reference("patient_id", PatientTable.id, onDelete = ReferenceOption.CASCADE)

}
/**
 * Tabela u kojoj se cuvaju podacima o receptima
 *
 * @property id Id recepta
 * @property doctorId Spoljni kljuc ka tabeli [DoctorTable], ko ga je prepisao
 * @property patientId Spoljni kljuc ka tabeli [PatientTable], kome je prepisan
 * @property medication Naziv leka
 * @property quantity Kolicina u kutijama ili tabletama
 * @property instructions Uputstvo za koriscenje leka
 * @property dateExpired Datum isteka recepta
 */
object RecipeTable: Table("recipes")
{
    val id=uuid("id").autoGenerate()
    override val primaryKey=PrimaryKey(id)
    val doctorId=reference("doctor_id", DoctorTable.id, onDelete = ReferenceOption.CASCADE)
    val patientId=reference("patient_id", PatientTable.id, onDelete = ReferenceOption.CASCADE)
    val medication=text("medication")
    val quantity=integer("quantity")
    val instructions=text("instructions")
    val dateExpired=date("date_expired")
}

/**
 * Tabela koja sluzi za cuvanje podataka o terminima za pacijente kod lekara
 * @property id Id termina
 * @property doctorId Id lekara, spoljni kljuc ka [DoctorTable]
 * @property patientId Id pacijenta, spoljni kljuc ka [PatientTable]
 * @property hospitalId Id bolnice, spoljni kljuc ka [HospitalTable]
 * @property startTime Pocetak termina u satima i minutima
 * @property endTime Kraj termina u satima i minutima
 * @property date Datum termina
 */
object TerminTable: Table("termins"){
    val id=uuid("id").autoGenerate()
    override val primaryKey=PrimaryKey(id)
    val doctorId=reference("doctor_id", DoctorTable.id, onDelete = ReferenceOption.CASCADE)
    val patientId=reference("patient_id", PatientTable.id, onDelete = ReferenceOption.CASCADE)
     val hospitalId=reference("hospital_id", HospitalTable.id, onDelete = ReferenceOption.CASCADE)
    val startTime=time("start_time")
    val endTime=time("end_time")
    val date=date("date")
    val status=enumeration<TerminStatus>("status")


}