package com.example.service

import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.recipe.RecipeServiceImplementation
import com.example.service.recipe.RecipeServiceInterface
/**
 * Klasa koja nasledjuje [RecipeServiceInterface]
 * @author Lazar Janković
 * @see RecipeServiceInterface
 * @see RecipeServiceInterfaceTest
 *
 */
class RecipeServiceImplementationTest: RecipeServiceInterfaceTest() {
    /**
     * Metoda koja vraca instancu klase koja nasledjuje [RecipeServiceInterface]
     * @return [RecipeServiceImplementation]
     */
    override fun getInstance(): RecipeServiceInterface {
        return RecipeServiceImplementation()
    }
}