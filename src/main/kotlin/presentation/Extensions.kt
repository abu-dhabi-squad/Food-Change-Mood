package presentation

import model.Food
import model.Nutrition
import presentation.ui_io.StringReader

fun Food.getFullDetails(): String {
    val strBuilder = StringBuilder()
    strBuilder.append("\n\n----- Meal -----")
    strBuilder.append("\nName: $name")
    strBuilder.append("\nMinutes: $minutes")
    strBuilder.append("\nSubmittedDate: $submittedDate")
    strBuilder.append("\nDescription: $description")
    strBuilder.append("\nTags: $tags")
    strBuilder.append("\nIngredients: $ingredients")
    strBuilder.append("\nSteps: $steps")
    strBuilder.append("\nNutrition:")
    strBuilder.append("\n${nutrition.getFullDetails("\t -")}")
    return strBuilder.toString()
}

fun Food.getNameAndDescription(): String {
    val strBuilder = StringBuilder()
    strBuilder.append("\nName: $name")
    strBuilder.append("\nDescription: $description")
    return strBuilder.toString()
}

fun Nutrition.getFullDetails(indent: String = ""): String {
    val strBuilder = StringBuilder()
    strBuilder.append("\n${indent}Calories: $calories")
    strBuilder.append("\n${indent}TotalFat: $totalFat")
    strBuilder.append("\n${indent}Sugar: $sugar")
    strBuilder.append("\n${indent}Sodium: $sodium")
    strBuilder.append("\n${indent}Protein: $protein")
    strBuilder.append("\n${indent}Saturated: $saturated")
    strBuilder.append("\n${indent}Carbohydrates: $carbohydrates")
    return strBuilder.toString()
}
