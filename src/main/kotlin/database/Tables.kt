package com.example.database

import com.example.domain.Role
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

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
