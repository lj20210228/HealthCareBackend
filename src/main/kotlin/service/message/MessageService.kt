package com.example.service.message

import com.example.domain.Message

/**
 * Interfejs u kome su navedene metode za rukovanje podacima o [Message]
 * @see Message
 * @author Lazar Jankovic
 */
interface MessageService {
    /**
     * Funckija za dodavanje nove poruke
     * @throws NullPointerException Ako je prosledjeni argument null
     * @return null ako nije uspesno dodat ili podaci o poruci ako jeste
     */
    suspend fun addMessage(message: Message?): Message?

    /**
     * Funkcija za trazenje poruke po id
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws NullPointerException Ako je prosledjeni argument prazan
     * @return Podaci o poruci ako je pronadjena ili null ako nije
     *
     */
    suspend fun getMessage(id: String?): Message?
    /**
     * Funkcija za trazenje poruka po id posiljaoca
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws NullPointerException Ako je prosledjeni argument prazan
     * @return Podaci o porukama ako su pronadjene ili prazna lista ako nije
     *
     */
    suspend fun getAllMessagesForRecipient(recipientId: String?): List<Message>
    /**
     * Funkcija za trazenje poruka po id primaoca
     * @throws NullPointerException Ako je prosledjeni argument null
     * @throws NullPointerException Ako je prosledjeni argument prazan
     * @return Podaci o porukama ako su pronadjene ili prazna lista ako nije
     *
     */
    suspend fun getAllMessagesForSender(senderId: String?):List<Message>
}