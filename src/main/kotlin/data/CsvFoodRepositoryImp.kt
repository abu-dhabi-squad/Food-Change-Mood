package data

import logic.repository.FoodRepository
import model.Food

class CsvFoodRepositoryImp(
    private val foodCsvParser: FoodCsvParser
) : FoodRepository {
    private val foods = mutableListOf<Food>()
    override fun getFoods(): Result<List<Food>> {
        if (foods.isEmpty()) {
            foodCsvParser.parseCsvFileToFoods().also { foods.addAll(it) }
        }
        return Result.success(foods)
    }
}