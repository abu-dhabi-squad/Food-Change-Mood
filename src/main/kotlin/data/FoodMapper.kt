package data

import model.Food
import model.Nutrition
import util.DateParser
import java.time.LocalDate
import java.time.format.DateTimeParseException

class FoodMapper(
    private val dateParserInterface: DateParser
) {
    fun parseFoodRow(row: List<String>): Food {
        return Food(
            name = row[ColumnIndex.NAME].trim(),
            id = row[ColumnIndex.ID].toInt(),
            minutes = row[ColumnIndex.MINUTES].toInt(),
            submittedDate = parseDate(row[ColumnIndex.SUBMITTED_DATE]),
            tags = parseStringToListOfString(row[ColumnIndex.TAGS]),
            nutrition = parseStringToListOfFloat(row[ColumnIndex.NUTRITION]).let {
                Nutrition(
                    sodium = it[NutritionColumnIndex.SODIUM],
                    saturated = it[NutritionColumnIndex.SATURATED],
                    carbohydrates = it[NutritionColumnIndex.CARBOHYDRATES],
                    sugar = it[NutritionColumnIndex.SUGAR],
                    calories = it[NutritionColumnIndex.CALORIES],
                    totalFat = it[NutritionColumnIndex.TOTAL_FAT],
                    protein = it[NutritionColumnIndex.PROTEIN]
                )
            },
            steps = parseStringToListOfString(row[ColumnIndex.STEPS]),
            description = row[ColumnIndex.DESCRIPTION].trim(),
            ingredients = parseStringToListOfString(row[ColumnIndex.INGREDIENTS])
        )
    }

    private fun parseDate(text: String): LocalDate? {
        return try {
            dateParserInterface.parseDateFromString(text)
        } catch (exception: DateTimeParseException) {
            null
        }
    }


    private fun parseStringToListOfString(text: String): List<String> =
        text.removeSurrounding("['", "']").removeSurrounding("['", "\"]").split("', '", ignoreCase = true)


    private fun parseStringToListOfFloat(text: String): List<Float> =
        text.removeSurrounding("[", "]").split(",").map { it.toFloat() }
}