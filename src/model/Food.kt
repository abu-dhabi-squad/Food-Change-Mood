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
)

data class Nutrition(
    val calories: Float,
    val totalFat: Float,
    val sugar: Float,
    val sodium: Float,
    val protein: Float,
    val saturated: Float,
    val carbohydrates: Float

)
