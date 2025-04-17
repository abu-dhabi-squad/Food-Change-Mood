package logic.usecase

import logic.repository.FoodRepository
import model.Food

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

        val meals = foodRepository.getFoods().getOrThrow()

        return meals.filter { food ->
            val mealName = food.name?.lowercase() ?: return@filter false
            isContainsPattern(mealName, input.lowercase())
        }
    }
}