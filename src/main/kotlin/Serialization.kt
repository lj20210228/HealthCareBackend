package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.User
import com.example.request.RegisterRequest
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.response.RegisterResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.*
import kotlin.reflect.KClass

fun Application.configureSerialization() {
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
        install(ContentNegotiation){
            json(
                Json {
                    prettyPrint=true
                    ignoreUnknownKeys=true
                    isLenient=true
                    encodeDefaults=true
                    coerceInputValues=true
                    encodeDefaults=true
                    classDiscriminator="type"
                    serializersModule= SerializersModule {

                        polymorphic(
                            Any::class,
                            actualClass = RegisterResponse::class,
                            actualSerializer = RegisterResponse.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = RegisterRequest::class,
                            actualSerializer = RegisterRequest.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = RegisterRequest::class,
                            actualSerializer = RegisterRequest.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = User::class,
                            actualSerializer = User.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = Hospital::class,
                            actualSerializer = Hospital.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = Doctor::class,
                            actualSerializer = Doctor.serializer()
                        )
                        polymorphic(
                            Any::class,
                            actualClass = Patient::class,
                            actualSerializer = Patient.serializer()
                        )





                    }
                }

            )
        }
    }


}

