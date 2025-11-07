package com.example.service.recipe

import com.example.database.DatabaseFactory
import com.example.database.RecipeTable
import com.example.domain.Recipe

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import java.io.File
import java.time.LocalDate
import java.util.Collections.list
import java.util.UUID

/**
 * Klasa koja implementira metode interfejsa [RecipeServiceInterface]
 * @author Lazar Janković
 * @see RecipeServiceInterface
 * @see Recipe
 */
class RecipeServiceImplementation: RecipeServiceInterface {


    /**
     * Metoda za dodavanje recepta
     */
    override suspend fun addRecipe(recipe: Recipe?): Recipe? {
        if (recipe==null)
            throw NullPointerException("Recept ne sme biti null")
        val list=getAllRecipes()
        if (list.contains(recipe))
            throw IllegalArgumentException("Recept za ove podatke i ovaj datum vec postoji")
        return DatabaseFactory.dbQuery {
            RecipeTable.insertReturning {
                it[doctorId]= UUID.fromString(recipe.getDoctorId())
                it[patientId]= UUID.fromString(recipe.getPatientId())
                it[medication]=recipe.getMedication()
                it[quantity]=recipe.getQuantity()
                it[instructions]=recipe.getInstructions()
                it[dateExpired]=recipe.getExpiredDate()
            }.map {
                rowToRecipe(it)
            }.first()
        }

    }

    /**
     * Metoda za pronalazenje recepta koji je prepisao neki lekar
     */
    override suspend fun getAllRecipesForDoctor(doctorId: String?): List<Recipe> {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")

        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.doctorId eq UUID.fromString(doctorId)
                }.mapNotNull {
                    rowToRecipe(it)
                }
            }
    }

    /**
     * Metoda kojima se vracaju recepti koji su prepisanu pacijentu
     */
    override suspend fun getAllRecipesForPatient(patientId: String?): List<Recipe> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta ne sme biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne sme biti prazan string")
        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.patientId eq UUID.fromString(patientId)
                }.mapNotNull {
                    rowToRecipe(it)
                }
            }
    }

    /**
     * Metoda koja pronalazi recept po id
     */
    override suspend fun getRecipeForId(recipeId: String?): Recipe? {
        if (recipeId==null)
            throw NullPointerException("Id recepta ne sme biti null")
        if (recipeId.isEmpty())
            throw IllegalArgumentException("Id recepta ne sme biti prazan string")
        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.id eq UUID.fromString(recipeId)
                }.map {
                    rowToRecipe(it)
                }.firstOrNull()
            }
    }

    /**
     * Metoda koja pronalazi listu recepata po imenu leka
     */

    override suspend fun getRecipesForMedication(medication: String?): List<Recipe> {
        if (medication==null)
            throw NullPointerException("Ime leka ne sme biti null")
        if (medication.isEmpty())
            throw IllegalArgumentException("Ime leka ne sme biti prazan string")
        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.medication regexp ("^${medication}\\y")
                }.mapNotNull {
                    rowToRecipe(it)
                }
            }
    }
    /**
     * Metoda za pronalazenje recepta koji je prepisao neki lekar, koji nisu istekli
     */
    override suspend fun getAllValidRecipesForDoctor(doctorId: String?): List<Recipe> {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if (doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")
        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.doctorId eq UUID.fromString(doctorId) and
                            (RecipeTable.dateExpired greater LocalDate.now())
                }.mapNotNull {
                    rowToRecipe(it)
                }
            }
    }

    /**
     * Metoda kojima se vracaju recepti koji su prepisanu pacijentu koji nisu istekli
     */
    override suspend fun getAllValidRecipesForPatient(patientId: String?): List<Recipe> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta ne sme biti null")
        if (patientId.isEmpty())
            throw IllegalArgumentException("Id pacijenta ne sme biti prazan string")

        return DatabaseFactory
            .dbQuery {
                RecipeTable.selectAll().where{
                    RecipeTable.patientId eq UUID.fromString(patientId) and
                            (RecipeTable.dateExpired greater LocalDate.now())
                }.mapNotNull {
                    rowToRecipe(it)
                }
            }
    }

    override suspend fun editRecipe(recipe: Recipe?): Recipe? {
        if (recipe==null){
            throw NullPointerException("Podaci za azuriranje moraju imati vrednost")
        }
        return DatabaseFactory
            .dbQuery {
                RecipeTable.updateReturning(where = {RecipeTable.id eq UUID.fromString(recipe.getId())} ) {
                    it[medication]=recipe.getMedication()
                    it[quantity]=recipe.getQuantity()
                    it[instructions]=recipe.getInstructions()
                    it[dateExpired]=recipe.getExpiredDate()
                }.map {
                    rowToRecipe(it)
                }.firstOrNull()
            }

    }

    override suspend fun deleteRecipe(recipeId: String?): Boolean {
        if (recipeId==null)
            throw NullPointerException("Id recepta koji je potrebno obrisati ne sme biti null")
        if(recipeId.isEmpty()){
            throw IllegalArgumentException("Id recepta koji je potrebno obrisati ne sme biti prazan")
        }
        val deletedRows= DatabaseFactory.dbQuery {
            RecipeTable.deleteWhere{
                RecipeTable.id eq UUID.fromString(recipeId)
            }
        }
        return deletedRows>0
    }

    /**
     * Funkcija koja pretvara red tabele u [Recipe] objekat
     * @param resultRow Red u tabeli recipes
     * @return null ako je prosledjeni red null, ako ne [Recipe] objekat
     */
    fun rowToRecipe(resultRow: ResultRow?): Recipe?{
        if (resultRow==null)
            return  null
        else return Recipe(
            id = resultRow[RecipeTable.id].toString(),
            patientId =resultRow[RecipeTable.patientId].toString(),
            doctorId =resultRow[RecipeTable.doctorId].toString(),
            medication = resultRow[RecipeTable.medication],
            quantity = resultRow[RecipeTable.quantity],
            instructions = resultRow[RecipeTable.instructions],
            dateExpired =resultRow[RecipeTable.dateExpired]
        )
    }

    /**
     * Funkcija za vracanje svih recepata iz baze
     * @return [List[Recipe]]
     */
    suspend fun getAllRecipes(): List<Recipe>{

        return DatabaseFactory.dbQuery {
            RecipeTable.selectAll().mapNotNull {
                rowToRecipe(it)
            }
        }

    }
}