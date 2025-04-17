package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.InvalidIdException

class GetMealByIdUseCase(
    private val foodRepository: FoodRepository
)
{
    fun getMealById(id: Int): Food {
        return foodRepository.getFoods()
            .getOrThrow()
            .find{it.id == id}
            ?: throw InvalidIdException()
    }
}