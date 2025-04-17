package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException

class MealSearchByNameUseCase(
    private val foodRepository: FoodRepository
) {
    private fun buildPrefixTable(pattern: String): IntArray {
        val table = IntArray(pattern.length)
        var length = 0
        var i = 1

        while (i < pattern.length) {
            when {
                pattern[i] == pattern[length] -> {
                    length++
                    table[i] = length
                    i++
                }
                length != 0 -> {
                    length = table[length - 1]
                }
                else -> {
                    table[i] = 0
                    i++
                }
            }
        }

        return table
    }

    private fun isContainsPattern(text: String, pattern: String): Boolean {
        if (pattern.isEmpty()) return true

        val prefixTable = buildPrefixTable(pattern)
        var textIndex = 0
        var patternIndex = 0

        while (textIndex < text.length) {
            if (text[textIndex] == pattern[patternIndex]) {
                textIndex++
                patternIndex++
                if (patternIndex == pattern.length) return true
            } else if (patternIndex != 0) {
                patternIndex = prefixTable[patternIndex - 1]
            } else {
                textIndex++
            }
        }

        return false
    }

    fun findMealsByName(input: String): List<Food> {

        return foodRepository.getFoods()
            .getOrThrow()
            .filter{ isMatchingMealByName(it, input) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealsFoundException(input)
    }

    private fun isMatchingMealByName(food: Food, input: String): Boolean {
        val mealName = food.name?.lowercase() ?: return false
        return isContainsPattern(mealName, input.lowercase())
    }
}