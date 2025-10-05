package com.example.repository.recipe

import com.example.domain.Recipe
import com.example.response.BaseResponse
import com.example.response.ListResponse


/**
 * Interfejs u kome se nalaze metode za rukovanje podacima o receptima na logickom nivou
 * @author Lazar Janković
 * @see Recipe
 * @see RecipeRepository
 * @see com.example.response.BaseResponse
 * @see com.example.response.ListResponse
 */
interface RecipeRepository {
    /**
     * Funkcija za slanje zahteva dodavanje novog recepta
     * @param recipe Recept koji se dodaje
     * @return [BaseResponse] Podaci o dodatom receptu ili poruka o gresci ako je neuspesno dodat
     */
    suspend fun addRecipe(recipe: Recipe?): BaseResponse<Recipe>

    /**
     * Funkcija za slanje zahteva vracanje svih recepata koje je lekar prepisao
     * @param doctorId Id lekara ciji se recepti traze
    
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom poruka o gresci
     */
    suspend fun getAllRecipesForDoctor(doctorId: String?): ListResponse<Recipe>
    /**
     * Funkcija za slanje zahteva vracanje svih recepata koji su prepisani pacijentu
     * @param patientId Id pacijenta ciji se recepti traze
  
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom poruka o gresci
     */
    suspend fun getAllRecipesForPatient(patientId: String?): ListResponse<Recipe>

    /**
     * Funkcija za slanje zahteva za pronalazenje odredjenog recepta po id
     * @param recipeId Id recepta koji se trazi
     * @return Ukoliko je recept pronadjen vracaju se podaci o njemu u suprotnom poruka o gresci
     */
    suspend fun getRecipeForId(recipeId: String?): BaseResponse<Recipe>

    /**
     * Funkcija za slanje zahteva pronalezenje svih recepata sa odredjenim imenom leka
     * @param medication Ime leka
     * @return [ListResponse[Recipe]] Ukoliko postoje recepti sa zadatim imenom leka vraca se lista recepata, ako ne poruka o gresci
     */
    suspend fun getRecipesForMedication(medication: String?): ListResponse<Recipe>
    /**
     * Funkcija za slanje zahteva za vracanje svih recepata koje je lekar prepisao,koji nisu istekli
     * @param doctorId Id lekara ciji se recepti traze
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom poruka o gresci
     */
    suspend fun getAllValidRecipesForDoctor(doctorId: String?): ListResponse<Recipe>
    /**
     * Funkcija za slanje zahrecavracanje svih recepata koji su prepisani pacijentu koji i dalje vaze
     * @param patientId Id pacijenta ciji se recepti traze
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom poruka o gresci
     */
    suspend fun getAllValidRecipesForPatient(patientId: String?): ListResponse<Recipe>

    /**
     * Funkcija za vracanje svih recepata koji su prepisani pacijentu koji i dalje vaze
     * @param jmbg Jmbg pacijenta ciji se recepti traze
     * @return Ukoliko se pronadju vraca se lista recepata, u suprotnom poruka o gresci
     */
    suspend fun getAllValidRecipesForPatientForJmbg(jmbg: String?): ListResponse<Recipe>

    /**
     * Funkcija koja salje zahtev za azuriranje recepta
     * @param recipe Recept koji treba azurirati
     * @return [BaseResponse] Ukoliko je recept uspesno azuriran vracaju se azurirani podaci i ispisuje se poruka o uspesnosti,
     * a ukoliko su prosledjeni losi podaci ili recept ne postoji ili nije uspesno azuriran poruka o gresci
     */
    suspend fun editRecipe(recipe: Recipe?): BaseResponse<Recipe>

    /**
     * Funkcija koja salje zahtev za brisanje recepta
     * @param recipeId Id recepta kojeg treba obrisati
     * @return [BaseResponse] Ukoliko je prosledjen los id recepta ili taj recept ne postoji vraca se poruka o gresci,
     * a ukoliko postoji vraca se poruka o uspesnom brisanju
     */
    suspend fun deleteRecipe(recipeId: String?): BaseResponse<Recipe>


}
