package logic.usecase

import logic.repository.FoodRepository
import model.EmptyHighCalorieListException
import model.Food

class GetMealForThinPeopleUseCase(
    private val foodRepository: FoodRepository
)
{
    fun getMeal(shownSet : MutableSet<Int>):Food{
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { onlyHighCaloriesData(it, shownSet) }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.random()
            ?: throw EmptyHighCalorieListException()
    }
    private fun onlyHighCaloriesData(food: Food, shownSet : MutableSet<Int>):Boolean{
        return food.name != null
                && food.description != null
                && food.nutrition.calories > LOW_CALORIES_MEAL_THRESHOLD
                && food.id !in shownSet
    }
    private companion object{
        const val LOW_CALORIES_MEAL_THRESHOLD = 700
    }
}