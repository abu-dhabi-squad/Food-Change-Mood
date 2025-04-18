package logic.usecase

import logic.repository.FoodRepository
import model.CountryMealsFetchException
import model.EmptyListException
import model.Food


class GetRandomMealsByCountryUseCase(
    private val foodRepository: FoodRepository
) {

    fun getRandomMeals(country: String): List<String> {

        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal -> meal.matchesCountry(country) }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.distinctBy { it.name?.lowercase()?.trim() }
            ?.mapNotNull { meal -> meal.name }
            ?.shuffled()
            ?.take(MAXIMUM_NUMBER_OF_MEALS)
            ?.sorted()
            ?: throw EmptyListException()
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