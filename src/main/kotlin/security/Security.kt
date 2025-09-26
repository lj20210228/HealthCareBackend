package com.example.security



import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*



/**
 * Konfiguriše JWT autentikaciju za Ktor aplikaciju.
 *
 * Ova funkcija inicijalizuje [JwtConfig] sa tajnim ključem
 * i instalira autentikacioni mehanizam u Ktor koristeći JWT.
 *
 * Korisnički ID se iz tokena dobija preko claim-a [JwtConfig.CLAIM]
 * i postavlja kao [UserIdPrincipal] koji se kasnije koristi u autentifikovanim rutama.
 *
 * @receiver [Application] na koju se primenjuje bezbednosna konfiguracija.
 */
fun Application.configureSecurity(){

    JwtConfig.initialize("health_care")
    install(Authentication){
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim=it.payload.getClaim(JwtConfig.CLAIM).asString()
                if(claim!=null){
                    UserIdPrincipal(claim)
                }else{
                    null
                }
            }
        }
    }

}