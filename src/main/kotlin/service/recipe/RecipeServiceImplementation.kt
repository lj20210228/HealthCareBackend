package com.example.service.recipe

import com.example.domain.Recipe
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate

/**
 * Klasa koja implementira metode interfejsa [RecipeServiceInterface]
 * @author Lazar Janković
 * @property file Fajl koji simulira kolonu u bazi
 * @property list Lista u koju se smestaju podaci o lekovima
 * @see RecipeServiceInterface
 * @see Recipe
 */
class RecipeServiceImplementation: RecipeServiceInterface {
    val file= File("recipes.json")
    val list=getAllRecipes()

    /**
     * Metoda za dodavanje recepta
     */
    override suspend fun addRecipe(recipe: Recipe?): Recipe {
        if (recipe==null)
            throw NullPointerException("Recept ne sme biti null")
        if (list.contains(recipe))
            throw IllegalArgumentException("Recept za ove podatke i ovaj datum vec postoji")
        list.add(recipe)
        val jsonString=Json { prettyPrint=true }.encodeToString(list)
        file.writeText(jsonString)
        return recipe

    }

    /**
     * Metoda za pronalazenje recepta koji je prepisao neki lekar
     */
    override suspend fun getAllRecipesForDoctor(doctorId: String?): List<Recipe> {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")
        return list.filter { it.getDoctorId()==doctorId }
    }

    /**
     * Metoda kojima se vracaju recepti koji su prepisanu pacijentu
     */
    override suspend fun getAllRecipesForPatient(patientId: String?): List<Recipe> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta ne sme biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne sme biti prazan string")
        return list.filter { it.getPatientId()==patientId }
    }

    /**
     * Metoda koja pronalazi recept po id
     */
    override suspend fun getRecipeForId(recipeId: String?): Recipe? {
        if (recipeId==null)
            throw NullPointerException("Id recepta ne sme biti null")
        if (recipeId.isEmpty())
            throw IllegalArgumentException("Id recepta ne sme biti prazan string")
        return list.find { it.getId()==recipeId }
    }

    /**
     * Metoda koja pronalazi listu recepata po imenu leka
     */

    override suspend fun getRecipesForMedication(medication: String?): List<Recipe> {
        if (medication==null)
            throw NullPointerException("Ime leka ne sme biti null")
        if (medication.isEmpty())
            throw IllegalArgumentException("Ime leka ne sme biti prazan string")
        return list.filter { it.getMedication().contains(medication, ignoreCase = true) }
    }
    /**
     * Metoda za pronalazenje recepta koji je prepisao neki lekar, koji nisu istekli
     */
    override suspend fun getAllValidRecipesForDoctor(doctorId: String?): List<Recipe> {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")
        return list.filter { it.getDoctorId()==doctorId&&it.getExpiredDate().isAfter(LocalDate.now()) }
    }

    /**
     * Metoda kojima se vracaju recepti koji su prepisanu pacijentu koji nisu istekli
     */
    override suspend fun getAllValidRecipesForPatient(patientId: String?): List<Recipe> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta ne sme biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne sme biti prazan string")
        return list.filter { it.getPatientId()==patientId &&it.getExpiredDate().isAfter(LocalDate.now())}
    }
    /**
     * Metoda kojima se vracaju recepti koji su prepisanu pacijentu, po pacijentovom jmbg, koji nisu istekli
     */
    override suspend fun getAllValidRecipesForPatientForJmbg(jmbg: String?): List<Recipe> {
        if (jmbg==null)
            throw NullPointerException("Id pacijenta ne sme biti null")
        if (jmbg.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne sme biti prazan string")
        if(!jmbg.all { it.isDigit() }){
            throw IllegalArgumentException("Jmbg mora sadrzati samo brojeve")

        }
        if(jmbg.length!=13){
            throw IllegalArgumentException("Duzina jmbg-a mora biti 13 cifara")

        }
        return list.filter { it.getPatientId()==jmbg &&it.getExpiredDate().isAfter(LocalDate.now())}
    }

    /**
     * Funkcija za pretvaranje json fajla u listu recepata
     */
    fun getAllRecipes(): MutableList<Recipe>{
        if(!file.exists()||file.readText().isBlank()) return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString(jsonString)
    }
}