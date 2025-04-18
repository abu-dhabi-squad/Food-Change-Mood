package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetRandomFoodUseCase(private val foodRepository: FoodRepository) {

    operator fun invoke(): Food {
        return foodRepository.getFoods().getOrThrow().random()
    }

}