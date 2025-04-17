package logic.usecase

import logic.repository.FoodRepository

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
}