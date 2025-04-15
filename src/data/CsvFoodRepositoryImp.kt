package data

import logic.repository.FoodRepository
import model.Food

class CsvFoodRepositoryImp (private val csvFileReader: CsvFileReader, private val foodCsvParser: FoodCsvParser) : FoodRepository {
    override fun getFoods(): Result<List<Food>> {
        val foods : MutableList<Food> = mutableListOf()
        csvFileReader.readLinesFromFile().take(5).forEach { lineOfCsv ->
            println(lineOfCsv)
            val newFood = foodCsvParser.parseOneLine(lineOfCsv)
            newFood?.apply {
                foods.add(newFood)
            }
        }
        return Result.success(foods)
    }
}