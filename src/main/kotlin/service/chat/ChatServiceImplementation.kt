package com.example.service.chat

import com.example.database.ChatTable
import com.example.database.DatabaseFactory
import com.example.domain.Chat
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

/**
 * Klasa koja implementira metode [ChatService]
 * @author Lazar Jankovic
 * @see ChatService
 * @see Chat
 */
class ChatServiceImplementation : ChatService{
    override suspend fun addChat(chat: Chat?): Chat? {
        if(chat==null)
            throw NullPointerException("Cet ne moze biti null")
        return DatabaseFactory.dbQuery {
            ChatTable.insertReturning {

                it[doctorId]= UUID.fromString(chat.getDoctorId())
                it[patientId]= UUID.fromString(chat.getPatientId())
            }.map {
                rowToChat(it)
            }.firstOrNull()
        }
    }

    override suspend fun getChat(id: String?): Chat? {
        if(id==null)
            throw NullPointerException("Id ceta ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id ceta ne moze biti prazan")
        return DatabaseFactory
            .dbQuery {
                ChatTable.selectAll().where {
                    ChatTable.id eq UUID.fromString(id)
                }.map {
                    rowToChat(it)
                }.firstOrNull()
            }
    }

    override suspend fun getChatsForPatient(patientId: String?): List<Chat> {
        if(patientId==null)
            throw NullPointerException("Id pacijenta u cetu ne moze biti null")
        if(patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta u cetu ne moze biti prazan")
        return DatabaseFactory
            .dbQuery {
                ChatTable.selectAll().where {
                    ChatTable.patientId eq UUID.fromString(patientId)
                }.mapNotNull {
                    rowToChat(it)
                }
            }
    }

    override suspend fun getChatsForDoctor(doctorId: String?): List<Chat> {
        if(doctorId==null)
            throw NullPointerException("Id lekara u cetu ne moze biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara u cetu ne moze biti prazan")
        return DatabaseFactory
            .dbQuery {
                ChatTable.selectAll().where {
                    ChatTable.doctorId eq UUID.fromString(doctorId)
                }.mapNotNull {
                    rowToChat(it)
                }
            }
    }

    override suspend fun getChats(): List<Chat> {

        return DatabaseFactory.dbQuery {
            ChatTable.selectAll().mapNotNull {
                rowToChat(it)
            }
        }
    }
    fun rowToChat(result: ResultRow?): Chat?{
        return if (result==null)
            null
        else
            Chat(
                id=result[ChatTable.id].toString()
                , patientId = result[ChatTable.patientId].toString()
                , doctorId = result[ChatTable.doctorId].toString()
            )
    }

}