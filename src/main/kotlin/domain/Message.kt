package com.example.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime
/**
 * Reprezentuje poruku koja se šalje između dva korisnika u okviru određenog četa.
 *
 * @property id Jedinstveni identifikator poruke.
 * @property senderId Identifikator korisnika koji je poslao poruku.
 * @property recipientId Identifikator korisnika koji prima poruku.
 * @property content Tekstualni sadržaj poruke.
 * @property timeStamp Vreme kada je poruka kreirana ili poslata.
 * @property chatId Identifikator četa kome poruka pripada.
 */
@Serializable
data class Message(

    private var id: String?=null,
    private var senderId: String,
    private var recipientId: String,
    private var content: String,
    @Contextual
    private var timeStamp: LocalDateTime?= LocalDateTime.now(),
    private var chatId: String?=null
){
    /**
     * Vraća identifikator poruke.
     * @return [String] id poruke ili `null` ako nije postavljen.
     */
    fun getId(): String?{
        return this.id
    }
    /**
     * Postavlja identifikator poruke.
     * @throws NullPointerException ako je prosleđeni id `null`.
     * @throws IllegalArgumentException ako je prosleđeni id prazan string.
     */
    fun setId(id:String?){
        if(id==null)
            throw NullPointerException("Id ne moze biti null")
        if(id.isEmpty())
            throw IllegalArgumentException("Id ne moze biti prazan")
        this.id=id
    }
    /**
     * Vraća identifikator pošiljaoca poruke.
     * @return [String] id pošiljaoca.
     */
    fun getSenderId(): String{
        return this.senderId
    }
    /**
     * Postavlja identifikator pošiljaoca poruke.
     * @throws NullPointerException ako je prosleđeni id `null`.
     * @throws IllegalArgumentException ako je prosleđeni id prazan string.
     */
    fun setSenderId(senderId:String?){
        if(senderId==null)
            throw NullPointerException("Id posiljaoca ne moze biti null")
        if(senderId.isEmpty())
            throw IllegalArgumentException("Id posiljaoca ne moze biti prazan")
        this.senderId=senderId
    }
    /**
     * Vraća identifikator primaoca poruke.
     * @return [String] id primaoca ili `null` ako nije postavljen.
     */
    fun getRecipientId(): String{
        return this.recipientId
    }
    /**
     * Postavlja identifikator primaoca poruke.
     * @throws NullPointerException ako je prosleđeni id `null`.
     * @throws IllegalArgumentException ako je prosleđeni id prazan string.
     */
    fun setRecipient(recipientId:String?){
        if(recipientId==null)
            throw NullPointerException("Id primaoca ne moze biti null")
        if(recipientId.isEmpty())
            throw IllegalArgumentException("Id primaoca ne moze biti prazan")
        this.recipientId=recipientId
    }
    /**
     * Vraća identifikator četa kome poruka pripada.
     * @return [String] id četa ili `null` ako nije postavljen.
     */
    fun getChatId(): String?{
        return this.chatId
    }
    /**
     * Postavlja identifikator četa kome poruka pripada.
     * @throws NullPointerException ako je prosleđeni id `null`.
     * @throws IllegalArgumentException ako je prosleđeni id prazan string.
     */
    fun setChatId(chatId:String?){
        if(chatId==null)
            throw NullPointerException("Id ceta ne moze biti null")
        if(chatId.isEmpty())
            throw IllegalArgumentException("Id ceta ne moze biti prazan")
        this.chatId=chatId
    }
    /**
     * Vraća sadržaj poruke.
     * @return [String] sadržaj poruke.
     */
    fun getContent(): String{
        return this.content
    }
    /**
     * Postavlja sadržaj poruke.
     * @throws NullPointerException ako je sadržaj `null`.
     * @throws IllegalArgumentException ako je sadržaj prazan string.
     */
    fun setContent(content: String?){
        if (content==null){
            throw NullPointerException("Sadrzaj poruke ne moze biti null")
        }
        if (content.isEmpty()){
            throw IllegalArgumentException("Sadrzaj poruke ne moze biti prazan")
        }
        this.content=content
    }
    /**
     * Vraća vreme kada je poruka kreirana ili poslata.
     * @return [LocalDateTime] vreme slanja poruke.
     */
    fun getTimeStamp(): LocalDateTime?{
        return this.timeStamp
    }
    /**
     * (Rezervisano) Postavlja vreme slanja poruke — trenutno ne implementirano jer timestamp
     * postavlja sistem automatski prilikom kreiranja objekta.
     */
    fun setTimeStamp(timeStamp: LocalDateTime?){

        if (timeStamp == null)
            throw NullPointerException("Vreme slanja ne moze biti null")
        this.timeStamp = timeStamp
    }

    /**
     * Proverava da li je trenutni objekat jednak drugom objektu.
     *
     * Dva objekta klase [Message] se smatraju jednakim ako imaju isti [id].
     *
     * @param other Objekat sa kojim se upoređuje trenutni objekat.
     * @return `true` ako su objekti isti ili imaju isti [id], `false` inače.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        return id == other.id
    }

    /**
     * Vraća hash kod trenutnog objekta.
     *
     * Hash kod je zasnovan na [id] polju.
     * Ako je [id] null, vraća 0.
     *
     * @return [Int] hash kod objekta.
     */
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    /**
     * Vraća tekstualnu reprezentaciju objekta [Message].
     *
     * Format prikazuje sva polja: [id], [senderId], [recipientId], [content], [timeStamp], [chatId].
     *
     * @return [String] opis objekta.
     */
    override fun toString(): String {
        return "Message(id=$id, senderId='$senderId', recipientId='$recipientId', content='$content', timeStamp=$timeStamp, chatId=$chatId)"
    }

}
