package model

import java.time.LocalDate

data class Food(
    val id: Int,
    val name: String?,
    val minutes: Int,
    val submittedDate: LocalDate?,
    val tags: List<String>,
    val nutrition: List<Nutrient>,
    val steps: List<String>,
    val description : String?,
    val ingredients: List<String>
)

data class Nutrient(
    val type: NutrientType,
    val value: Float
)

enum class NutrientType {
    CALORIES, TOTAL_FAT, SUGAR, SODIUM, PROTEIN, SATURATED, CARBOHYDRATES
}

