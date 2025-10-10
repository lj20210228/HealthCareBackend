package com.example.repository.message

import com.example.domain.Message
import com.example.response.BaseResponse
import com.example.response.ListResponse

interface MessageRepository {
    /**
     * Funkcija koja salje zahtev za dodavanje poruke
     * @return [BaseResponse[Message]] Vraca se odgovor sa porukom o gresci ili sa podacima o poruci ako je dodata
     */
    suspend fun addMessage(message: Message?): BaseResponse<Message>

    /**
     * Funkcija koja salje zahtev za vracanje poruke po njenom id
     * @return [BaseResponse[Message]] Ukoliko je pronadjena vracaju se podaci o njoj ili poruka o gresci ako nije
     */
    suspend fun getMessage(messageId: String?): BaseResponse<Message>

    /**
     * Funkcija koja ce vracati sve poruke koje je neko poslao
     * @return [ListResponse[Message]] Ukoliko postoje vraca se lista poruka, ukoliko ne vraca se poruka o gresci
     */
    suspend fun getMessagesForRecepient(recepientId: String?): ListResponse<Message>

    /**
     * Funkcija koja vraca sve primljene poruke za nekog korisnika
     * @return [ListResponse[Message]] Ukoliko postoje vraca se lista poruka, ukoliko ne vraca se poruka o gresci
     */
    suspend fun getMessagesForSender(senderId: String?): ListResponse<Message>
}