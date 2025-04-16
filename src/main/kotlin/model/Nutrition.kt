package model

data class Nutrition(
    val calories: Float,
    val totalFat: Float,
    val sugar: Float,
    val sodium: Float,
    val protein: Float,
    val saturated: Float,
    val carbohydrates: Float
) {
    fun print(indent: String = "") {
        println("${indent}Calories: $calories")
        println("${indent}TotalFat: $totalFat")
        println("${indent}Sugar: $sugar")
        println("${indent}Sodium: $sodium")
        println("${indent}Protein: $protein")
        println("${indent}Saturated: $saturated")
        println("${indent}Carbohydrates: $carbohydrates")
    }
}