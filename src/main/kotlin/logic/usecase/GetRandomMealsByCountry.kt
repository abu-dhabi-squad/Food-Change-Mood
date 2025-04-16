package logic.usecase

import logic.repository.FoodRepository
import model.CountryMealsFetchException
import model.Food

class GetRandomMealsByCountry(
    private val foodRepository: FoodRepository
) {

    fun getRandomMeals(country: String): List<String> {

        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal -> meal.matchesCountry(country)
            }.takeIf { meals -> meals.isNotEmpty() }
            ?.mapNotNull { meal -> meal.name }
            ?.shuffled()
            ?.take(20)
            ?.sorted()
            ?: throw CountryMealsFetchException(message = "Failed to fetch meals for the given country")

    }

    private fun Food.matchesCountry(country: String): Boolean {

        return tags.any { tag -> tag.contains(country.lowercase()) } == true
                || name?.lowercase()?.contains(country.lowercase()) == true
                || description?.lowercase()?.contains(country.lowercase()) == true
    }

}