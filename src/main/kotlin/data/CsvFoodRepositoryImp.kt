package data

import logic.repository.FoodRepository
import model.Food

class CsvFoodRepositoryImp(
    private val foodParser: FoodParser
) : FoodRepository {
    private val foods = mutableListOf<Food>()
    override fun getFoods(): Result<List<Food>> {
        if (foods.isEmpty()) {
            foodParser.parse().also { foods.addAll(it)
            }
        }
        return Result.success(foods)
    }
}