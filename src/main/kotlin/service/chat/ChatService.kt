package com.example.service.chat

import com.example.domain.Chat
import java.net.IDN

/**
 * Interfejs u kome su navedene metode za rukovanje podacima o chat
 * @see com.example.domain.Chat
 * @author Lazar Jankovic
 *
 */
interface ChatService {
    /**
     * Funkcija za dodavanje novog ceta
     * @throws NullPointerException Ako je prosledjeni argument null
     * @return null ako cet nije uspesno dodat ili objekat klase [Chat] ako je uspesno dodat
     */
    suspend fun addChat(chat: Chat?): Chat?

    /**
     * Funkcija za pronalazenje [Chat] po id
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan
     * @return null ako nije pronadjen ili objekat [Chat] ako jeste
     *
     */
    suspend fun getChat(id: String?): Chat?
    /**
    * Funkcija za pronalazenje svih cetova za pacijenta
    * @throws NullPointerException Ako je prosledjeni argument null
    * @throws IllegalArgumentException Ako je prosledjeni argument prazan
    * @return lista [Chat] ako je pronadjeno ili prazna lista ako nije
    *
    */
    suspend fun getChatsForPatient(patientId: String?): List<Chat>
    /**
     * Funkcija za pronalazenje svih cetova za lekara
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws IllegalArgumentException Ako je prosledjeni argument prazan
     * @return lista [Chat] ako je pronadjeno ili prazna lista ako nije
     *
     */
    suspend fun getChatsForDoctor(doctorId: String?): List<Chat>


    /**
     * Funkcija koja vraca sve chat, koristice se za provere u logickom sloju
     * @return [List[Chat]] Ako su nadjeni lista, ako ne prazna lista
     */
    suspend fun getChats(): List<Chat>
}