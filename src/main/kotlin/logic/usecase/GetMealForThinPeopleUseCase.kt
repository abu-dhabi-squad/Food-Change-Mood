package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetMealForThinPeopleUseCase(
    private val foodRepository: FoodRepository
)
{
    private var mealIndex = 0
    fun getMeal():Food{
        foodRepository.getFoods()
            .fold(
                onSuccess = {
                    meals ->
                    return meals.filter(::onlyHighCaloriesData)
                        .sortedByDescending { it.nutrition.calories }
                        .takeIf (::isTherelikableMeal)
                        ?.get(mealIndex)
                        ?: throw Exception("there is no high calories meals in list")
                            },
                onFailure = {throw Exception("there is no food in list")},
            )
    }

    private fun onlyHighCaloriesData(food: Food):Boolean{
        return food.name != null && food.description != null && food.nutrition.calories > 700.0
    }
    private fun isTherelikableMeal(meals: List<Food>):Boolean{
        return meals.isNotEmpty() && meals.size > mealIndex
    }
    fun dislikeTheCurrentMeal(){
        mealIndex++
    }
}