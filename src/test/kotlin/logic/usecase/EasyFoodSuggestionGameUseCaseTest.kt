package logic.usecase

import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class EasyFoodSuggestionGameUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var easyFoodSuggestionGameUseCase: EasyFoodSuggestionGameUseCase

    @BeforeEach
    fun setup(){
        foodRepository = mockk()
        easyFoodSuggestionGameUseCase = EasyFoodSuggestionGameUseCase(foodRepository)
    }


}