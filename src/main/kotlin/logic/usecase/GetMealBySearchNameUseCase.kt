package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException

class GetMealBySearchNameUseCase(
    private val foodRepository: FoodRepository
) {
    private fun buildPrefixTable(pattern: String): IntArray {
        val table = IntArray(pattern.length)
        var prefixLength = 0
        var indexOfCurrentChar = 1

        while (indexOfCurrentChar < pattern.length) {
            when {
                pattern[indexOfCurrentChar] == pattern[prefixLength] -> {
                    prefixLength++
                    table[indexOfCurrentChar] = prefixLength
                    indexOfCurrentChar++
                }
                prefixLength != 0 -> {
                    prefixLength = table[prefixLength - 1]
                }
                else -> {
                    table[indexOfCurrentChar] = 0
                    indexOfCurrentChar++
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
            ?: throw NoMealsFoundException()
    }

    private fun isMatchingMealByName(food: Food, input: String): Boolean {
        val mealName = food.name?.lowercase() ?: return false
        return isContainsPattern(mealName, input.lowercase())
    }
}