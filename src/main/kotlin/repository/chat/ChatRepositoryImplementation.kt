package com.example.repository.chat

import com.example.UserService
import com.example.domain.Chat
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.chat.ChatService
import com.example.service.patient.PatientServiceInterface
import com.example.service.user.UserServiceInterface

/**
 * Klasa koja implementira metode [ChatRepository]
 * @author Lazar Jankovic  Detail: constraint fk_messages_chat_id__id on table messages depends on table chat

 * @see ChatRepository
 * @see Chat
 * @see PatientServiceInterface
 * @see DoctorServiceInterface
 * @see ChatService
 */
class ChatRepositoryImplementation(
    val userService: UserServiceInterface,
    val service: ChatService
): ChatRepository {
    override suspend fun addChat(chat: Chat?): BaseResponse<Chat> {
       if (chat==null)
           return BaseResponse.ErrorResponse(message = "Cet koji dodajete ne moze biti null")
        val chats=service.getChats()
        if (chats.contains(chat))
            return BaseResponse.ErrorResponse(message = "Cet vec postoji")
        val result=service.addChat(chat)
        if (result==null)
            return BaseResponse.ErrorResponse(message = "Cet nije uspesno dodat")
        return BaseResponse.SuccessResponse(data = result, message = "Cet uspesno dodat")

    }

    override suspend fun getChat(chatId: String?): BaseResponse<Chat> {
        if (chatId==null)
            return BaseResponse.ErrorResponse(message = "Id ceta koji trazite ne moze biti null")
        if (chatId.isEmpty())
            return BaseResponse.ErrorResponse(message = "Id ceta koji trazite ne moze biti prazan")
        val result=service.getChat(chatId)

        if (result==null)
            return BaseResponse.ErrorResponse(message = "Cet nije pronadjen")
        return BaseResponse.SuccessResponse(data = result, message = "Cet uspesno pronadjen")
    }

    override suspend fun getChatsForPatient(patientId: String?): ListResponse<Chat> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Id pacijenta cije cetove  trazite ne moze biti null")
        if (patientId.isEmpty())
            return ListResponse.ErrorResponse(message = "Id pacijenta cije cetove  trazite ne moze biti prazan")
        val patientExist=userService.getUserById(patientId)
        if (patientExist==null)
            return ListResponse.ErrorResponse(message = "Pacijent ne postoji")
        val result=service.getChatsForPatient(patientId)
        if (result.isEmpty())
            return ListResponse.ErrorResponse(message = "Cetovi za ovog pacijenta ne postoje")
        return ListResponse.SuccessResponse(data = result, message = "Cetovi za ovog pacijenta uspesno pronadjeni")
    }

    override suspend fun getChatsForDoctor(doctorId: String?): ListResponse<Chat> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Id lekara cije cetove  trazite ne moze biti null")
        if (doctorId.isEmpty())
            return ListResponse.ErrorResponse(message = "Id lekara cije cetove  trazite ne moze biti prazan")
        val doctorExist=userService.getUserById(doctorId)
        if (doctorExist==null)
            return ListResponse.ErrorResponse(message = "Lekar ne postoji")
        val result=service.getChatsForDoctor(doctorId)
        if (result.isEmpty())
            return ListResponse.ErrorResponse(message = "Cetovi za ovog lekara ne postoje")
        return ListResponse.SuccessResponse(data = result, message = "Cetovi za ovog lekara uspesno pronadjeni")
    }


}