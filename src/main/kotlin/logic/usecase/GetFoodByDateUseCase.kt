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
            .filter { meal-> meal.isHighQuality() && meal.submittedDate==date }
            .takeIf { it.isNotEmpty() }
            ?.mapNotNull { food -> food.name?.let{food.id to it} }
            ?: throw EmptySearchByDateListException()
    }

    private fun Food.isHighQuality(): Boolean {
        return name != null && description != null
    }
}