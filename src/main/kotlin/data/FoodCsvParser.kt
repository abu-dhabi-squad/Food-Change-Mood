package data

import model.Food
import model.Nutrition
import util.DateParserInterface
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class FoodCsvParser(
    private val csvFile: File,
    private val dateParserInterface: DateParserInterface
) {

    fun parseCsvFileToFoods(): List<Food> {
        return if (csvFile.exists()) {
            getFoodsList()
        } else emptyList()
    }

    private fun getFoodsList(): List<Food> {
        return parseCsvLines().drop(1)
            .asSequence()
            .map { parseFoodRow(it) }
            .toList()
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var insideQuotes = false
        var i = 0

        while (i < line.length) {
            val c = line[i]
            when (c) {
                '"' -> {
                    if (insideQuotes && i + 1 < line.length && line[i + 1] == '"') {
                        current.append('"')
                        i++
                    } else {
                        insideQuotes = !insideQuotes
                    }
                }

                ',' -> {
                    if (insideQuotes) current.append(c)
                    else {
                        result.add(current.toString())
                        current.clear()
                    }
                }

                else -> current.append(c)
            }
            i++
        }
        result.add(current.toString())
        return result
    }

    private fun parseCsvLines(): List<List<String>> {
        val rows = mutableListOf<List<String>>()
        var buffer = StringBuilder()
        var insideQuotes = false
        csvFile.forEachLine { rawLine ->
            buffer.appendLine(rawLine)
            val quoteCount = rawLine.count { it == '"' }
            insideQuotes =
                if (insideQuotes) quoteCount % 2 == 0 else quoteCount % 2 != 0

            if (!insideQuotes) {
                val parsed = parseCsvLine(buffer.toString().trim())
                rows.add(parsed)
                buffer.clear()
            }
        }
        return rows
    }

    private fun parseFoodRow(row: List<String>): Food {
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