package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.InvalidIdException

class GetMealByIdUseCase(
    private val foodRepository: FoodRepository
)
{
    fun getMealById(id: Int): Food {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter{it.isHighQuality()}
            .find{it.id == id}
            ?: throw InvalidIdException()
    }
    private fun Food.isHighQuality(): Boolean {
        return name != null && description != null
    }
}

/**
 * fun `should throw exception when repository fails`() {
 *         // Given
 *         val exception = RuntimeException("Network error")
 *         every { foodRepository.getFoods() } returns Result.failure(exception)
 *
 *         // When & Then
 *         val thrown = Assertions.assertThrows(RuntimeException::class.java) {
 *             getIraqiMealsUseCase.getAllIraqiMeals()
 *         }
 *         Assertions.assertEquals("Network error", thrown.message)
 *     }
 * yyv-qidp-dfd*/