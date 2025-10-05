package com.example.repository.recipe

import com.example.domain.Recipe
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.patient.PatientServiceInterface
import com.example.service.recipe.RecipeServiceInterface
import com.fasterxml.jackson.databind.ser.Serializers

/**
 * Klasa koja implementira metode [RecipeRepository]
 * @see RecipeRepository
 * @see Recipe
 * @see PatientServiceInterface
 * @see RecipeServiceInterface
 * @author Lazar Jankovic
 */
class RecipeRepositoryImplementation(val patientService: PatientServiceInterface,val serviceInterface: RecipeServiceInterface): RecipeRepository {
    override suspend fun addRecipe(recipe: Recipe?): BaseResponse<Recipe> {
        if (recipe==null)
            return BaseResponse.ErrorResponse(message = "Ne mozete dodati recept bez podataka")

        return try {
            val added = serviceInterface.addRecipe(recipe)

            if (added == null) {
                BaseResponse.ErrorResponse(message = "Recept nije uspesno dodat")
            } else {
                BaseResponse.SuccessResponse(data = added, message = "Recept uspesno dodat")
            }
        } catch (e: IllegalArgumentException) {
            BaseResponse.ErrorResponse(message = e.message)
        }
    }

    override suspend fun getAllRecipesForDoctor(doctorId: String?): ListResponse<Recipe> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o lekaru")
        val recipes=serviceInterface.getAllRecipesForDoctor(doctorId)
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Nije prepisan ni jedan recept")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun getAllRecipesForPatient(patientId: String?): ListResponse<Recipe> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o pacijentu")
        val recipes=serviceInterface.getAllRecipesForPatient(patientId)
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Nije prepisan ni jedan recept")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun getRecipeForId(recipeId: String?): BaseResponse<Recipe> {
        if (recipeId==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o receptu")
        val recipe=serviceInterface.getRecipeForId(recipeId)
        if (recipe==null)
            return BaseResponse.ErrorResponse(message = "Recept nije pronadjen")
        return BaseResponse.SuccessResponse(data = recipe, message = "Recept uspesno pronadjen")
    }

    override suspend fun getRecipesForMedication(medication: String?): ListResponse<Recipe> {
        if (medication==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o leku")
        val recipes=serviceInterface.getRecipesForMedication(medication)
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Nema recepata sa ovim lekom")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun getAllValidRecipesForDoctor(doctorId: String?): ListResponse<Recipe> {
        if (doctorId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o lekaru")
        val recipes=serviceInterface.getAllValidRecipesForDoctor(doctorId)
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Nije prepisan ni jedan recept koji i dalje vazi")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun getAllValidRecipesForPatient(patientId: String?): ListResponse<Recipe> {
        if (patientId==null)
            return ListResponse.ErrorResponse(message = "Niste prosledili ispravne podatke o pacijentu")
        val recipes=serviceInterface.getAllValidRecipesForPatient(patientId)
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Nije prepisan ni jedan recept koji i dalje vazi")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun getAllValidRecipesForPatientForJmbg(jmbg: String?): ListResponse<Recipe> {
        if (jmbg==null)
            return ListResponse.ErrorResponse(message = "Niste uneli ispravan jmbg")
        val patient=patientService.getPatientByJmbg(jmbg)
        if (patient==null)
            return ListResponse.ErrorResponse(message = "Pacijent ne postoji")
        val recipes=serviceInterface.getAllValidRecipesForPatient(patient.getId())
        if (recipes.isEmpty())
            return ListResponse.ErrorResponse(message = "Ne postoje vazeci recepti za ovog pacijenta")
        return ListResponse.SuccessResponse(data = recipes, message = "Recepti uspesno pronadjeni")
    }

    override suspend fun editRecipe(recipe: Recipe?): BaseResponse<Recipe> {
        if (recipe==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili podatke za azuriranje")
        val exist=serviceInterface.getRecipeForId(recipe.getId())
        if (exist==null)
            return BaseResponse.ErrorResponse(message = "Recept ne postoji")
        val recipe=serviceInterface.editRecipe(recipe)
        if (recipe==null)
            return BaseResponse.ErrorResponse(message = "Recept nije uspesno azuziran")
        return BaseResponse.SuccessResponse(data = recipe, message = "Recept uspesno azuriran")
    }

    override suspend fun deleteRecipe(recipeId: String?): BaseResponse<Recipe> {
        if (recipeId==null)
            return BaseResponse.ErrorResponse(message = "Niste prosledili ispravne podatke za brisanje")
        val exist=serviceInterface.getRecipeForId(recipeId)
        if (exist==null)
            return BaseResponse.ErrorResponse(message = "Recept ne postoji")
        val recipe=serviceInterface.deleteRecipe(recipeId)
        if (recipe==false)
            return BaseResponse.ErrorResponse(message = "Recept nije obrisan")
        return BaseResponse.SuccessResponse( message = "Recept je obrisan")
    }
}