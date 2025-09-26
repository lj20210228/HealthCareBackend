package com.example.database

import com.example.domain.Role
import org.jetbrains.exposed.sql.Table

object UserTable: Table("users")
{
    val id=uuid("id").autoGenerate()
    override val primaryKey= PrimaryKey(id)
    val email=varchar("email",256)
    val password=text("password")
    val role=enumeration<Role>("role")
}
