package com.example.repository.chat

import com.example.domain.Chat
import com.example.response.BaseResponse
import com.example.response.ListResponse

/**
 * Metode za logicki sloj za rukovanje podacima o [Chat]
 * @author Lazar Jankovic
 * @see Chat
 * @see com.example.service.chat.ChatService
 * @see BaseResponse
 * @see ListResponse
 */
interface ChatRepository {
    /**
     * Funckija koja salje zahtev za dodavanje ceta u tabelu
     * @return [BaseResponse[Chat]] Vracaju se podaci o cetu ako je uspesno dodat ili poruka o gresci ukoliko nije
     */
    suspend fun addChat(chat: Chat?): BaseResponse<Chat>

    /**
     * Metoda koja salje zahtev za pronalazenje ceta po id
     * @return [BaseResponse[Chat]] Ukoliko je pronadjen vracaju se podaci o njemu, ukoliko ne poruka o gresci
     */
    suspend fun getChat(chatId:String?): BaseResponse<Chat>

    /**
     * Metoda koja salje zahtev za vracanje svih cetova pacijenta
     * @return [ListResponse[Chat]] Ukoliko ih ima vracaju se podaci o njima u suprotnom poruka o gresci
     */
    suspend fun getChatsForPatient(patientId:String?): ListResponse<Chat>
    /**
     * Metoda koja salje zahtev za vracanje svih cetova lekara
     * @return [ListResponse[Chat]] Ukoliko ih ima vracaju se podaci o njima u suprotnom poruka o gresci
     */
    suspend fun getChatsForDoctor(doctorId: String?): ListResponse<Chat>
}