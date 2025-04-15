package data


import model.Food
import model.Nutrient
import model.NutrientType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class FoodCsvParser {

    fun parseOneLine(lineOfCsv: String) : Food? {
        val foodInfo = lineOfCsv.split(",")
        try {
            return Food(
                id = foodInfo[ColumnIndex.ID].toInt(),
                name = foodInfo[ColumnIndex.NAME],
                minutes = foodInfo[ColumnIndex.MINUTES].toInt(),
                submittedDate = parseFromStringToLocalDate(foodInfo[ColumnIndex.SUBMITTED_DATE]),
                tags = parseList(foodInfo[ColumnIndex.TAGS]),
                nutrition = parseNutrientList(foodInfo[ColumnIndex.NUTRITION]),
                steps = parseList(foodInfo[ColumnIndex.STEPS]),
                description = foodInfo[ColumnIndex.DESCRIPTION],
                ingredients = parseList(foodInfo[ColumnIndex.N_INGREDIENTS])
            )
        } catch (exception: Exception) {
            println("Parsing error $exception")
            return null
        }
    }

    private fun parseFromStringToLocalDate(date: String) : LocalDate? {
        try {
            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
            return LocalDate.parse(date, formatter)
        } catch (ex : DateTimeParseException){
            return null
        }
    }

    private fun parseList(listString: String) : List<String> = listString.split(",")

    private fun parseNutrientList(nutrientString: String) : List<Nutrient> {
        val result = mutableListOf<Nutrient>()
        val nutrientValues = nutrientString.split(",")
        for ((index, nutrient) in NutrientType.entries.withIndex()) {
            result.add(Nutrient(nutrient, nutrientValues[index].toFloat()))
        }
        return result
    }

}