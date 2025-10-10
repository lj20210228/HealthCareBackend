package com.example.domainTests

import com.example.domain.Message
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


/**
 * Test klasa koja proverava ispravnost ponašanja domenske klase [Message].
 *
 * Testira sve osnovne gettere i settere, kao i situacije kada se očekuje
 * izbacivanje izuzetaka (NullPointerException i IllegalArgumentException)
 * u slučaju nevalidnih vrednosti.
 *
 * Svaki test je nezavisan i koristi novi instancirani objekat [Message].
 */
class MessageTests {

    /** Instanca klase [Message] koja se koristi u testovima. */
    private var message: Message? = null
    private lateinit var message2: Message
    private lateinit var message3: Message

    /**
     * Inicijalizuje novi objekat [Message] pre svakog testa.
     *
     * Kreira poruku sa validnim vrednostima za sva polja.
     */
    @BeforeEach
    fun setUp() {
        message = Message(
            id = "1",
            senderId = "1",
            recipientId = "1",
            content = "Poruka poslata lekaru",
            chatId = "1"
        )
        message2 = Message(
            id = "1",
            senderId = "C",
            recipientId = "D",
            content = "Hi",
            chatId = "chat2"
        )
        message3 = Message(
            id = "2",
            senderId = "A",
            recipientId = "B",
            content = "Hello",
            chatId = "chat1"
        )
    }

    /**
     * Resetuje instancu klase [Message] nakon svakog testa.
     * Obezbeđuje da testovi budu nezavisni jedni od drugih.
     */
    @AfterEach
    fun tearDown() {
        message = null
    }

    /**
     * Proverava da li metoda [Message.getRecipientId] vraća ispravan ID primaoca.
     */
    @Test
    fun getRecipientId_test() {
        assertEquals("1", message?.getRecipientId())
    }

    /**
     * Proverava da li metoda [Message.getSenderId] vraća ispravan ID pošiljaoca.
     */
    @Test
    fun getSenderId_test() {
        assertEquals("1", message?.getSenderId())
    }

    /**
     * Proverava da li metoda [Message.getChatId] vraća ispravan ID četa.
     */
    @Test
    fun getChatId_test() {
        assertEquals("1", message?.getChatId())
    }

    /**
     * Proverava da li metoda [Message.getContent] vraća ispravan sadržaj poruke.
     */
    @Test
    fun getContent_test() {
        assertEquals("Poruka poslata lekaru", message?.getContent())
    }

    /**
     * Proverava da li metoda [Message.setId] baca [NullPointerException]
     * kada se prosledi `null` vrednost.
     */
    @Test
    fun setId_null() {
        assertThrows<NullPointerException> {
            message?.setId(null)
        }
    }

    /**
     * Proverava da li metoda [Message.setChatId] baca [NullPointerException]
     * kada se prosledi `null` vrednost.
     */
    @Test
    fun setChat_null() {
        assertThrows<NullPointerException> {
            message?.setChatId(null)
        }
    }

    /**
     * Proverava da li metoda [Message.setRecipient] baca [NullPointerException]
     * kada se prosledi `null` vrednost.
     */
    @Test
    fun setRecipient_null() {
        assertThrows<NullPointerException> {
            message?.setRecipient(null)
        }
    }

    /**
     * Proverava da li metoda [Message.setSenderId] baca [NullPointerException]
     * kada se prosledi `null` vrednost.
     */
    @Test
    fun setSender_null() {
        assertThrows<NullPointerException> {
            message?.setSenderId(null)
        }
    }

    /**
     * Proverava da li metoda [Message.setContent] baca [NullPointerException]
     * kada se prosledi `null` vrednost.
     */
    @Test
    fun setContent_null() {
        assertThrows<NullPointerException> {
            message?.setContent(null)
        }
    }

    /**
     * Proverava da li metoda [Message.setId] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setId_prazan() {
        assertThrows<IllegalArgumentException> {
            message?.setId("")
        }
    }

    /**
     * Proverava da li metoda [Message.setChatId] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setChat_prazan() {
        assertThrows<IllegalArgumentException> {
            message?.setChatId("")
        }
    }

    /**
     * Proverava da li metoda [Message.setRecipient] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setRecipient_prazan() {
        assertThrows<IllegalArgumentException> {
            message?.setRecipient("")
        }
    }

    /**
     * Proverava da li metoda [Message.setSenderId] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setSender_prazan() {
        assertThrows<IllegalArgumentException> {
            message?.setSenderId("")
        }
    }

    /**
     * Proverava da li metoda [Message.setContent] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setContent_prazan() {
        assertThrows<IllegalArgumentException> {
            message?.setContent("")
        }
    }

    /**
     * Proverava da li metoda [Message.setId] uspešno postavlja novi ID.
     */
    @Test
    fun setId_ispravno() {
        message?.setId("2")
        assertEquals("2", message?.getId())
    }

    /**
     * Proverava da li metoda [Message.setRecipient] uspešno postavlja novi ID primaoca.
     */
    @Test
    fun setRecipientId_ispravno() {
        message?.setRecipient("2")
        assertEquals("2", message?.getRecipientId())
    }

    /**
     * Proverava da li metoda [Message.setSenderId] uspešno postavlja novi ID pošiljaoca.
     */
    @Test
    fun setSenderId_ispravno() {
        message?.setSenderId("2")
        assertEquals("2", message?.getSenderId())
    }

    /**
     * Proverava da li metoda [Message.setChatId] uspešno postavlja novi ID četa.
     */
    @Test
    fun setChatId_ispravno() {
        message?.setChatId("2")
        assertEquals("2", message?.getChatId())
    }

    /**
     * Proverava da li metoda [Message.setContent] uspešno postavlja novi sadržaj poruke.
     */
    @Test
    fun setContent_ispravno() {
        message?.setContent("Poruka")
        assertEquals("Poruka", message?.getContent())
    }

    /**
     * Proverava da li metoda [equals] vraća `true` za objekte sa istim ID-em.
     */
    @Test
    fun equals_sameId_returnsTrue() {
        assertEquals(message, message2)
    }

    /**
     * Proverava da li metoda [equals] vraća `false` za objekte sa različitim ID-em.
     */
    @Test
    fun equals_differentId_returnsFalse() {
        assertNotEquals(message, message3)
    }

    /**
     * Proverava da li metoda [hashCode] vraća isti hash za objekte sa istim ID-em.
     */
    @Test
    fun hashCode_sameId_returnsSameHash() {
        assertEquals(message.hashCode(), message2.hashCode())
    }

    /**
     * Proverava da li metoda [hashCode] vraća različit hash za objekte sa različitim ID-em.
     */
    @Test
    fun hashCode_differentId_returnsDifferentHash() {
        assertNotEquals(message.hashCode(), message3.hashCode())
    }

    /**
     * Proverava da li metoda [toString] sadrži sve relevantne informacije o poruci.
     */
    @Test
    fun toString_containsAllFields() {
        val str = message.toString()
        assertTrue(str.contains("1"))
        assertTrue(str.contains("1"))
        assertTrue(str.contains("1"))
        assertTrue(str.contains("Poruka poslata lekaru"))
        assertTrue(str.contains("1"))
    }
}
