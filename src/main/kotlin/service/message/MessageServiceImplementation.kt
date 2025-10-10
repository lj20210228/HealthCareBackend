package com.example.service.message

import com.example.database.DatabaseFactory
import com.example.database.MessageTable
import com.example.domain.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

/**
 * Klasa koja implementira metode [MessageService]
 * @author Lazar Jankovic
 * @see MessageService
 * @see Message
 */
class MessageServiceImplementation: MessageService {
    override suspend fun addMessage(message: Message?): Message? {
        if (message==null)
            throw NullPointerException("Poruka ne moze biti null")
        return DatabaseFactory.dbQuery {
            MessageTable.insertReturning {
                it[senderId]= message.getSenderId()
                it[recipientId]= message.getRecipientId()
                it[chatId]= UUID.fromString(message.getChatId())
                it[content]=message.getContent()

            }.map {
                rowToMessage(it)
            }.firstOrNull()
        }


    }

    override suspend fun getMessage(id: String?): Message? {
        if (id==null)
            throw NullPointerException("Id poruke ne moze biti null")
        if (id.isEmpty())
            throw NullPointerException("Id poruke ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            MessageTable.selectAll().where{
                MessageTable.id eq UUID.fromString(id)
            }.map {
                rowToMessage(it)
            }.firstOrNull()
        }
    }

    override suspend fun getAllMessagesForRecipient(recipientId: String?): List<Message> {
        if (recipientId==null)
            throw NullPointerException("Id posiljaoca ne moze biti null")
        if (recipientId.isEmpty())
            throw NullPointerException("Id posiljaoca ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            MessageTable.selectAll().where{
                MessageTable.recipientId eq recipientId
            }.mapNotNull {
                rowToMessage(it)
            }
        }
    }

    override suspend fun getAllMessagesForSender(senderId: String?): List<Message> {
        if (senderId==null)
            throw NullPointerException("Id primaoca ne moze biti null")
        if (senderId.isEmpty())
            throw NullPointerException("Id primaoca ne moze biti prazan")
        return DatabaseFactory.dbQuery {
            MessageTable.selectAll().where{
                MessageTable.senderId eq senderId
            }.mapNotNull {
                rowToMessage(it)
            }
        }
    }
    fun rowToMessage(resultRow: ResultRow?): Message?{
        if(resultRow==null)
            return null
        else return Message(
            id = resultRow[MessageTable.id].toString(),
            senderId = resultRow[MessageTable.senderId].toString(),
            recipientId = resultRow[MessageTable.recipientId].toString(),
            content =resultRow[MessageTable.content],
            timeStamp = resultRow[MessageTable.timestamp],
            chatId = resultRow[MessageTable.chatId].toString()
        )
    }
}