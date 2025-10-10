package com.example.repository.message

import com.example.UserService
import com.example.domain.Chat
import com.example.domain.Message
import com.example.domain.Role
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.chat.ChatService
import com.example.service.message.MessageService
import com.example.service.patient.PatientServiceInterface
import com.example.service.user.UserServiceInterface

/**
 * Klasa koja implementira [MessageRepository]
 * @author Lazar Jankovic
 * @see MessageRepository
 * @see Message
 * @see ChatService
 * @see UserServiceInterface
 */
class MessageRepositoryImplementation(
    val service: MessageService,
    val chatService: ChatService,
    val userService: UserServiceInterface,
): MessageRepository {
    override suspend fun addMessage(message: Message?): BaseResponse<Message> {
        if (message==null)
            return BaseResponse.ErrorResponse(message="Poruka ne moze biti null")

        var chat=message.getChatId()?.let { chatService.getChat(it) }
        if (chat==null){
            val recipient=userService.getUserById(message.getRecipientId())
            if (recipient==null)
                return BaseResponse.ErrorResponse(message="Posiljaoc poruke ne postoji")

            val sender=userService.getUserById(message.getSenderId())
            if (sender==null)
                return BaseResponse.ErrorResponse(message="Primaoc poruke ne postoji")
            val patientId=when(sender.getRole()){
                 Role.ROLE_PATIENT->{
                     sender.getId()
                }
                else-> recipient.getId()
            }
            val doctorId=when(sender.getRole()){
                Role.ROLE_DOCTOR->{
                    sender.getId()
                }
                else->recipient.getId()
            }

            chat=chatService.addChat(Chat(doctorId = doctorId!! , patientId = patientId!!))
        }
        val message=service.addMessage(message.copy(chat?.getId()))
        return BaseResponse.SuccessResponse(data = message,"Poruka uspesno dodata")

    }

    override suspend fun getMessage(messageId: String?): BaseResponse<Message> {
        if (messageId==null)
            return BaseResponse.ErrorResponse(message="Niste prosledili podatke o poruci")
        val exist=service.getMessage(messageId)
        if (exist==null)
            return BaseResponse.ErrorResponse(message="Poruka ne postoji")
        return BaseResponse.SuccessResponse(data = exist,message="Poruka pronadjena")
    }

    override suspend fun getMessagesForRecepient(recepientId: String?): ListResponse<Message> {
        if (recepientId==null){
            return ListResponse.ErrorResponse(message = "Niste prosledili posiljaoca")
        }
       val messages=service.getAllMessagesForRecipient(recepientId)
        if (messages.isEmpty()){
            return ListResponse.ErrorResponse(message = "Ovaj korisnik nije poslao ni jednu poruku")
        }
        return ListResponse.SuccessResponse(data = messages,"Poruke uspesno pronadjene")

    }

    override suspend fun getMessagesForSender(senderId: String?): ListResponse<Message> {

        if (senderId==null){
            return ListResponse.ErrorResponse(message = "Niste prosledili posiljaoca")
        }
        val messages=service.getAllMessagesForSender(senderId)
        if (messages.isEmpty()){
            return ListResponse.ErrorResponse(message = "Ovaj korisnik nije primio ni jednu poruku")
        }
        return ListResponse.SuccessResponse(data = messages,"Poruke uspesno pronadjene")

    }
}