package logic.usecase

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.InvalidIdException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetMealByIdUseCaseTest {
    private lateinit var getMealByIdUseCase: GetMealByIdUseCase
    private val foodRepository: FoodRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMealByIdUseCase = GetMealByIdUseCase(foodRepository)
    }

    @Test
    fun `getMealById should call getFood function only once when it's called`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<InvalidIdException> {
            getMealByIdUseCase.getMealById(2200)
        }
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `getMealById should throw InvalidIdException when getFood function return empty list`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<InvalidIdException> {
            getMealByIdUseCase.getMealById(2200)
        }
    }

    @Test
    fun `getMealById should throw InvalidIdException when data is low quality as name is null`() {
        //given
        val data = mutableListOf(createMeal(2000, null, "description 1"))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<InvalidIdException> {
            getMealByIdUseCase.getMealById(2000)
        }
    }

    @Test
    fun `getMealById should throw InvalidIdException when data is low quality as description is null`() {
        //given
        val data = mutableListOf(createMeal(2000, "name1", null))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<InvalidIdException> {
            getMealByIdUseCase.getMealById(2000)
        }
    }

    @Test
    fun `getMealById should throw InvalidIdException when data does not have the id`() {
        //given
        val data = mutableListOf(
            createMeal(2100, "name1", "description1"),
            createMeal(2200, "name2", "description2"),
            createMeal(2300, "name3", "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<InvalidIdException> {
            getMealByIdUseCase.getMealById(2000)
        }
    }

    @Test
    fun `getMealById should return the meal that matches the id when data does have the id and high quality data`() {
        //given
        val data = mutableListOf(
            createMeal(2100, "name1", "description1"),
            createMeal(2200, "name2", "description2"),
            createMeal(2300, "name3", "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealByIdUseCase.getMealById(2100)).isEqualTo(createMeal(2100, "name1", "description1"))
    }
}

