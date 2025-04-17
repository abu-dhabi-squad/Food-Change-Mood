package logic.usecase

import logic.repository.FoodRepository
import model.EmptySearchByDateListException
import model.Food
import java.time.LocalDate


class GetFoodByDateUseCase(
    private val foodRepository: FoodRepository
) {
    fun getMealsByDate(date: LocalDate): List<Pair<Int,String>>{
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { isOnlyHighQualityDataAndTheRequiredDate(it,date) }
            .takeIf { it.isNotEmpty() }
            ?.map { it.id to it.name!! }
            ?: throw EmptySearchByDateListException()
    }
    fun getMealById(id: Int): Food {
        return foodRepository.getFoods()
            .getOrThrow()
            .find{it.id == id}!!
    }
    private fun isOnlyHighQualityDataAndTheRequiredDate(food: Food, date: LocalDate):Boolean{
        return food.name != null && food.description != null && food.submittedDate == date
    }
}