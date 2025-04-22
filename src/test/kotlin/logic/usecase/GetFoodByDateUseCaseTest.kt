package logic.usecase

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.EmptySearchByDateListException
import model.Food
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class GetFoodByDateUseCaseTest{
    private lateinit var getFoodByDateUseCase: GetFoodByDateUseCase
    private val foodRepository: FoodRepository = mockk(relaxed = true)

    @BeforeEach
    fun setUp(){
        getFoodByDateUseCase = GetFoodByDateUseCase(foodRepository)
    }

    @Test
    fun `getMealsByDate should call getFood function only once when it's called`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        org.junit.jupiter.api.assertThrows<EmptySearchByDateListException> {
            getFoodByDateUseCase.getMealsByDate(LocalDate.now())
        }
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `getMealsByDate should throw EmptySearchByDateListException when getFood function return empty list`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        org.junit.jupiter.api.assertThrows<EmptySearchByDateListException> {
            getFoodByDateUseCase.getMealsByDate(LocalDate.now())
        }
    }

    @Test
    fun `getMealsByDate should throw EmptySearchByDateListException when data is low quality as name is null`() {
        //given
        val data = mutableListOf(createMeal(2000,null, LocalDate.now(),"description 1"))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        org.junit.jupiter.api.assertThrows<EmptySearchByDateListException> {
            getFoodByDateUseCase.getMealsByDate(LocalDate.now())
        }
    }

    @Test
    fun `getMealsByDate should throw EmptySearchByDateListException when data is low quality as description is null`() {
        //given
        val data = mutableListOf(createMeal(2000,"name1", LocalDate.now(),null))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        org.junit.jupiter.api.assertThrows<EmptySearchByDateListException> {
            getFoodByDateUseCase.getMealsByDate(LocalDate.now())
        }
    }

    @Test
    fun `getMealsByDate should throw EmptySearchByDateListException when data does not have meal submitted in that date`() {
        //given
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.of(2002,1,10),"description1"),
            createMeal(2200,"name2", LocalDate.of(2012,1,14),"description2"),
            createMeal(2300,"name3", LocalDate.of(2003,3,10),"description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptySearchByDateListException> {
            getFoodByDateUseCase.getMealsByDate(LocalDate.of(2004,3,10))
        }
    }

    @Test
    fun `getMealsByDate should return only the data that matches the date when the data is high quality`() {
        //given
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.of(2002,1,10),"description1"),
            createMeal(2200,"name2", LocalDate.of(2012,1,14),"description2"),
            createMeal(2300,"name3", LocalDate.of(2003,3,10),"description3"),
            createMeal(2400,"name4", LocalDate.of(2003,3,10),"description4")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getFoodByDateUseCase.getMealsByDate(LocalDate.of(2003,3,10)))
            .isEqualTo(listOf(2300 to "name3", 2400 to "name4"))
    }

    private fun createMeal(
        id: Int,
        name: String?,
        submittedDate: LocalDate?,
        description: String?,
    ): Food {
        return Food(
            id,
            name,
            minutes = 3,
            submittedDate,
            tags = listOf(),
            nutrition = Nutrition(calories = 0f, totalFat = 0f, sugar = 0f, sodium = 0f, protein = 0f, saturated = 0f, carbohydrates = 0f),
            steps = listOf(),
            description,
            ingredients = listOf()
        )
    }


}