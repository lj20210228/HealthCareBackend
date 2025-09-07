package com.example.service.recipe

import com.example.domain.Recipe

/**
 * Interfejs u kome se nalaze metode za rukovanje podacima o receptima
 * @author Lazar Janković
 * @see Recipe
 */
interface RecipeServiceInterface {
    /**
     * Funkcija za dodavanje novog recepta
     * @param recipe Recept koji se dodaje
     * @throws NullPointerException Ako je [recipe] null baca se izuzetak
     * @throws IllegalArgumentException AKo [recipe] vec postojo
     * @return [Recipe] Podaci o dodatom receptu
     */
    suspend fun addRecipe(recipe: Recipe?): Recipe

    /**
     * Funkcija za vracanje svih recepata koje je lekar prepisao
     * @param doctorId Id lekara ciji se recepti traze
     * @throws NullPointerException Ako je [doctorId] null
     * @throws IllegalArgumentException Ako je [doctorId] prazan string
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom prazna lista
     */
    suspend fun getAllRecipesForDoctor(doctorId: String?): List<Recipe>
    /**
     * Funkcija za vracanje svih recepata koji su prepisani pacijentu
     * @param patientId Id pacijenta ciji se recepti traze
     * @throws NullPointerException Ako je [patientId] null
     * @throws IllegalArgumentException Ako je [patientId] prazan string
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom prazna lista
     */
    suspend fun getAllRecipesForPatient(patientId: String?): List<Recipe>

    /**
     * Funkcija za pronalazenje odredjenog recepta po id
     * @param recipeId Id recepta koji se trazi
     * @throws NullPointerException Ako je [recipeId] null
     * @throws IllegalArgumentException Ako je [recipeId] prazan string
     * @return Ukoliko je recept pronadjen vracaju se podaci o njemu u suprotnom null
     */
    suspend fun getRecipeForId(recipeId: String?): Recipe?

    /**
     * Funkcija za pronalezenje svih recepata sa odredjenim lekom
     * @param medication Ime leka
     * @throws NullPointerException Ukoliko je [medication] null
     * @throws IllegalArgumentException Ukoliko je [medication] prazan string
     * @return [List[Recipe]] Ukoliko postoje recepti sa zadatim imenom leka vraca se lista recepata, ako ne prazna lista
     */
    suspend fun getRecipesForMedication(medication: String?): List<Recipe>
    /**
     * Funkcija za vracanje svih recepata koje je lekar prepisao,koji nisu istekli
     * @param doctorId Id lekara ciji se recepti traze
     * @throws NullPointerException Ako je [doctorId] null
     * @throws IllegalArgumentException Ako je [doctorId] prazan string
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom prazna lista
     */
    suspend fun getAllValidRecipesForDoctor(doctorId: String?): List<Recipe>
    /**
     * Funkcija za vracanje svih recepata koji su prepisani pacijentu koji i dalje vaze
     * @param patientId Id pacijenta ciji se recepti traze
     * @throws NullPointerException Ako je [patientId] null
     * @throws IllegalArgumentException Ako je [patientId] prazan string
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom prazna lista
     */
    suspend fun getAllValidRecipesForPatient(patientId: String?): List<Recipe>
}