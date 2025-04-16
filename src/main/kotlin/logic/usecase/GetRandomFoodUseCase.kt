package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealFoundException


class GetRandomFoodUseCase(private val foodRepository: FoodRepository) {

    operator fun invoke(): Result<Food> {
        return foodRepository.getFoods().fold(
            onSuccess = ::onGetMealSuccess,
            onFailure = ::onGetMealsFailure
        )
    }

    private fun onGetMealSuccess(meals: List<Food>): Result<Food> {
        return if (meals.isNotEmpty())
            Result.success(meals.random())
        else Result.failure(NoMealFoundException("There are no meal found."))
    }


    private fun onGetMealsFailure(throwable: Throwable): Result<Food> = Result.failure(throwable)

}