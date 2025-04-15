package data

import logic.repository.FoodRepository
import model.Food

class CsvFoodRepositoryImp(
    private val csvFileReader: CsvFileReader,
    private val foodCsvParser: FoodCsvParser
) : FoodRepository {
    override fun getFoods(): Result<List<Food>> {
        TODO("Not yet implemented")
    }
}