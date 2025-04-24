package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetRandomFoodUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getRandomFoodUseCase: GetRandomFoodUseCase

    @BeforeEach
    fun setup(){
        foodRepository = mockk()
        getRandomFoodUseCase = GetRandomFoodUseCase(foodRepository)
    }

    @Test
    fun `should return random meal when food repository return success with non empty food list`(){
        // given
       var foods =  listOf(createFood(id = 1),createFood(id = 2),createFood(id = 3))
        every { foodRepository.getFoods() } returns  Result.success(foods)
       // when
        val food = getRandomFoodUseCase.invoke()
        // then
        assertThat(foods).contains(food)
        verify (exactly = 1){ foodRepository.getFoods() }
    }

    @Test
    fun `should throw exception when food repository return  failure`(){
        // given
        every { foodRepository.getFoods() } returns  Result.failure(Exception())
        // when && then
        assertThrows<Exception>  {
            getRandomFoodUseCase.invoke()
        }
    }

    @Test
    fun `should throw exception when food repository return  success with empty list`(){
        // given
        every { foodRepository.getFoods() } returns  Result.success(emptyList())
        // when && then
        assertThrows<Exception>  {
            getRandomFoodUseCase.invoke()
        }
    }


}