package com.example.date

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Serializer koji obezbeđuje da se vremena serijalizuju i deserijalizuju
 * isključivo u formatu 09:00 Ako se prosledi drugačiji format,
 * baca se [IllegalArgumentException].
 * @author Lazar Janković
 * @property descriptor Opisuje kakav tip podataka ce biti prosledjen JSON-u
 * @property formatter  Formater koji formatira datum u format "08"00"
 *
 * @see java.time.LocalTime
 * @see java.time.format.DateTimeFormatter
 */
object StrictLocalTimeSerializer: KSerializer<LocalTime> {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")
    override val descriptor: SerialDescriptor
            = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    /**
     * Funckija koja LocalTime pretvara u string
     * @param value [LocalTime] objekat koji treba pretvoriti u string
     * @param encoder Enkoder koji upisuje podatke u JSON
     */
    override fun serialize(
        encoder: Encoder,
        value: LocalTime
    ) {
        encoder.encodeString(value.format(StrictLocalTimeSerializer.formatter))


    }

    /**
     * Deserijalizuje string iz JSON-a u objekat tipa [LocalTime].
     * Dozvoljen je isključivo format "08:00"
     *
     * @param decoder Dekoder koji čita podatke iz JSON-a.
     * @return Objekat tipa [LocalTime] koji odgovara prosleđenom stringu.
     *
     * @throws IllegalArgumentException Ako je string u pogrešnom formatu.
     */
    override fun deserialize(decoder: Decoder): LocalTime {
        val timeStr=decoder.decodeString()
        try {
            return LocalTime.parse(timeStr, StrictLocalTimeSerializer.formatter)
        }catch (e: DateTimeParseException){
            throw IllegalArgumentException("Vreme  mora biti u formatu 09:00")
        }    }

}