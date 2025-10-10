package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Chat
import com.example.domain.Hospital
import com.example.domain.Message
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.message.MessageRepository
import com.example.repository.message.MessageRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.chat.ChatService
import com.example.service.chat.ChatServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.message.MessageService
import com.example.service.message.MessageServiceImplementation
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import io.ktor.server.sessions.DEFAULT_SESSION_MAX_AGE
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test klasa za [MessageRepository]
 * @author Lazar Jankovic
 * @see MessageRepository
 */
class MessageRepositoryTests {

    private lateinit var chatService: ChatService
    private lateinit var userServiceInterface: UserServiceInterface
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var chat: Chat
    private lateinit var message: Message
    private lateinit var message2: Message
    private lateinit var message3: Message
    private lateinit var repository: MessageRepository
    private lateinit var hosptal: Hospital
    private lateinit var hospitalService: HospitalServiceInterface

    @BeforeEach
    fun setUp(){
        DatabaseFactory.init()
        DatabaseFactory.clearTable("users")
        DatabaseFactory.clearTable("chat")
        DatabaseFactory.clearTable("messages")
        DatabaseFactory.clearTable("hospital")

        chatService= ChatServiceImplementation()
        userServiceInterface= UserServiceImplementation()
        repository= MessageRepositoryImplementation(
            service = MessageServiceImplementation(),
            chatService = chatService,
            userService = userServiceInterface
        )
        hosptal= Hospital(
            name = "Opsta bolnica",
            city = "Cacak",
            address = "Milosa Obrenovica 23"
        )
        hospitalService= HospitalServiceImplementation()

        runBlocking {
            hosptal=hospitalService.addHospital(hospital = hosptal)!!
        }
        user1= User(

            email = "pera@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR
        )
        user2= User(

            email = "mika@mikic.com",
            password = "Password123!",
            role = Role.ROLE_PATIENT
        )

        runBlocking {
            user1=userServiceInterface.addUser(user1)!!
            user2=userServiceInterface.addUser(user2)!!

        }
        chat= Chat(
            doctorId = user1.getId()!!,
            patientId = user2.getId()!!
        )
        runBlocking {
            chat=chatService.addChat(chat)!!
        }
        message= Message(
            senderId = user2.getId()!!,
            recipientId = user1.getId()!!,
            content = "Poruka",
            chatId =chat.getId()!!
        )
        message= Message(
            senderId = user1.getId()!!,
            recipientId = user2.getId()!!,
            content = "Poruka",
            chatId =chat.getId()!!
        )
    }
    @Test
    fun addTest_null()=runBlocking{
        val response=repository.addMessage(null)
        assertTrue(response is BaseResponse.ErrorResponse)
        assertEquals("Poruka ne moze biti null",response.message)
    }
    @Test
    fun addTest_posiljalacNePostoji()=runBlocking {

        val response=repository.addMessage(message.copy(recipientId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9",))
        assertTrue(response is BaseResponse.ErrorResponse)
        assertEquals("Posiljalac poruke ne postoji",response.message)
    }
    @Test
    fun addTest_primalacNePostoji()=runBlocking {
        val response=repository.addMessage(message.copy(senderId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9"))
        assertTrue(response is BaseResponse.ErrorResponse)
        assertEquals("Primalac poruke ne postoji",response.message)
    }
    @Test
    fun addTest_uspesno()=runBlocking {
        val response=repository.addMessage(message)
        assertTrue(response is BaseResponse.SuccessResponse)
        assertEquals("Poruka uspesno dodata",response.message)
    }

    @Test
    fun getMessageForId_Testnull()=runBlocking {
        val response=repository.getMessage(null)
        assertTrue(response is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke o poruci",response.message)

    }
    @Test
    fun getMessageForId_TestNePostoji()=runBlocking {
        val response=repository.getMessage("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(response is BaseResponse.ErrorResponse)
        assertEquals("Poruka ne postoji",response.message)

    }
    @Test
    fun getMessageForId_TestUspesno()=runBlocking {
        val message=(repository.addMessage(message) as BaseResponse.SuccessResponse).data
        val response=repository.getMessage(message?.getId())
        assertTrue(response is BaseResponse.SuccessResponse)
        assertEquals("Poruka pronadjena",response.message)

    }
    @Test
    fun getMessagesForRecipient_Testnull()=runBlocking {
        val response=repository.getMessagesForRecepient(null)
        assertTrue(response is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili posiljaoca",response.message)

    }
    @Test
    fun getMessagesForRecipient_TestNePostoji()=runBlocking {
        val response=repository.getMessagesForRecepient(user1.getId())
        assertTrue(response is ListResponse.ErrorResponse)
        assertEquals("Ovaj korisnik nije poslao ni jednu poruku",response.message)

    }
    @Test
    fun getMessagesForRecipient_TestUspesno()=runBlocking {
        val message=(repository.addMessage(message) as BaseResponse.SuccessResponse).data
        val response=repository.getMessagesForRecepient(user2.getId())
        println(response)
        assertTrue(response is ListResponse.SuccessResponse)
        assertEquals("Poruke uspesno pronadjene",response.message)

    }
    @Test
    fun getMessageForSender_Testnull()=runBlocking {
        val response=repository.getMessagesForSender(null)
        assertTrue(response is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili primaoca",response.message)

    }
    @Test
    fun getMessagesForSender_TestNePostoji()=runBlocking {
        val response=repository.getMessagesForSender(user2.getId())
        assertTrue(response is ListResponse.ErrorResponse)
        assertEquals("Ovaj korisnik nije primio ni jednu poruku",response.message)

    }
    @Test
    fun getMessagesForSender_TestUspesno()=runBlocking {
        val message=(repository.addMessage(message) as BaseResponse.SuccessResponse).data
        val response=repository.getMessagesForSender(user1.getId())
        assertTrue(response is ListResponse.SuccessResponse)
        assertEquals("Poruke uspesno pronadjene",response.message)

    }

}