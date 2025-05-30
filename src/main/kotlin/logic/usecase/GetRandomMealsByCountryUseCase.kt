package logic.usecase

import logic.repository.FoodRepository
import model.Food
import logic.takeShuffled
import model.NoMealsFoundException


class GetRandomMealsByCountryUseCase(
    private val foodRepository: FoodRepository
) {

    fun getRandomMeals(country: String): List<String> {

        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal -> meal.matchesCountry(country) && country.isNotEmpty() }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.distinctBy { it.name?.lowercase()?.trim() }
            ?.mapNotNull { meal -> meal.name }
            ?.takeShuffled(MAXIMUM_NUMBER_OF_MEALS)
            ?.sorted()
            ?: throw NoMealsFoundException()
    }

    private fun Food.matchesCountry(country: String): Boolean {

        return listOf<List<String>>(
            name?.lowercase()?.split(" ") ?: emptyList(),
            description?.lowercase()?.split(" ") ?: emptyList(),
            tags.map { it.lowercase() }).flatten().toSet().any { word -> word == country }
    }

    companion object{

        const val MAXIMUM_NUMBER_OF_MEALS = 20
    }
}