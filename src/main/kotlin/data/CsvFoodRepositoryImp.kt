package data

import logic.repository.FoodRepository
import model.Food

class CsvFoodRepositoryImp(
    private val foodCsvParser: FoodCsvParser
) : FoodRepository {
    override fun getFoods(): Result<List<Food>> {
        return Result.success(foodCsvParser.parseCsvFileToFoods())
    }
}