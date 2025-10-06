package com.example.routes

import com.example.domain.Recipe
import com.example.repository.recipe.RecipeRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.jetbrains.exposed.sql.idParam

fun Route.recipeRoutes(repository: RecipeRepository){
    post("/recipe/add") {
        val params=call.receive<Recipe>()
        val recipe=repository.addRecipe(params)
        call.respond(status = HttpStatusCode.fromValue(recipe.statusCode),recipe)


    }
    get ("/recipe/{id}"){
        val param=call.parameters["id"]
        val recipe=repository.getRecipeForId(param)
        call.respond(status = HttpStatusCode.fromValue(recipe.statusCode),recipe)


    }
    get("recipe/medication/{medication}") {
        val medication=call.parameters["medication"]
        val recipes=repository.getRecipesForMedication(medication)
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    get("recipe/doctor/{doctorId}") {
        val doctorId=call.parameters["doctorId"]
        val recipes=repository.getAllValidRecipesForDoctor(doctorId =doctorId )
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    get("recipe/patient/{patientId}") {
        val patientId=call.parameters["patientId"]
        val recipes=repository.getAllRecipesForPatient(patientId )
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    get("recipe/doctor/valid/{doctorId}") {
        val doctorId=call.parameters["doctorId"]
        val recipes=repository.getAllValidRecipesForDoctor(doctorId =doctorId )
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    get("recipe/patient/valid/{patientId}") {
        val patientId=call.parameters["patientId"]
        val recipes=repository.getAllValidRecipesForPatient(patientId )
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    get("recipe/patient/valid/jmbg/{jmbg}") {
        val jmbg=call.parameters["jmbg"]
        val recipes=repository.getAllValidRecipesForPatientForJmbg(jmbg )
        call.respond(status = HttpStatusCode.fromValue(recipes.statusCode),recipes)

    }
    put("recipe/edit") {
        val params=call.receive<Recipe>()
        val recipeEdited=repository.editRecipe(params)
        call.respond(status = HttpStatusCode.fromValue(recipeEdited.statusCode),recipeEdited)

    }
    delete("recipe/delete/{id}") {
        val recipeId=call.parameters["id"]
        val response=repository.deleteRecipe(recipeId)
        call.respond(status = HttpStatusCode.fromValue(response.statusCode),response)
    }


}