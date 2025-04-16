package logic.usecase

import logic.repository.FoodRepository
import model.CountryMealsFetchException

class GetRandomMealsByCountry(
    private val foodRepository: FoodRepository
){

    fun getRandomMeals(country: String) : List<String>{

        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal ->
                meal.tags.any { tag ->
                    tag.contains(country, ignoreCase = true)
                } == true
            }.takeIf { meals -> meals.isNotEmpty() }
            ?.mapNotNull { meal -> meal.name }
            ?.shuffled()
            ?.take(20)
            ?: throw CountryMealsFetchException(message = "Failed to fetch meals for the given country")

    }
}