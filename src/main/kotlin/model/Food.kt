package model

import java.time.LocalDate

data class Food(
    val id: Int,
    val name: String?,
    val minutes: Int,
    val submittedDate: LocalDate?,
    val tags: List<String>,
    val nutrition: Nutrition,
    val steps: List<String>,
    val description : String?,
    val ingredients: List<String>
) {
    fun print() {
        println("\n----- Meal -----")
        println("Name: $name")
        println("Minutes: $minutes")
        println("SubmittedDate: $submittedDate")
        println("Description: $description")
        println("Tags: $tags")
        println("Ingredients: $ingredients")
        println("Steps: $steps")
        println("Nutrition:")
        nutrition.print("\t- ")
    }
}


