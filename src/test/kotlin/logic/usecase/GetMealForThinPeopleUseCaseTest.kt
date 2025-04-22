package logic.usecase

import com.google.common.truth.Truth
import com.sun.net.httpserver.Authenticator.Success
import data.CsvFoodRepositoryImp
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.AppException
import model.EmptyHighCalorieListException
import model.Food
import model.Nutrition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate

class GetMealForThinPeopleUseCaseTest {
    private lateinit var getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase
    private val foodRepository: CsvFoodRepositoryImp = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMealForThinPeopleUseCase = GetMealForThinPeopleUseCase(foodRepository)
    }


    @Test
    fun `getMeal should call getFood function only once when it's called`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when getFood function return empty list`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data is low quality one as name is null`() {
        //given
        val data = mutableListOf(createMeal(2000,null, LocalDate.now(),710f,"description 1"))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data is low quality one as description is null`() {
        //given
        val data = mutableListOf(createMeal(2000,"name1", LocalDate.now(),710f,null))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data does not have high calorie meals`() {
        //given
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.now(),70f,"description1"),
            createMeal(2200,"name2", LocalDate.now(),700f,"description2"),
            createMeal(2300,"name3", LocalDate.now(),200f,"description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should contain all high calorie meals when there is not unliked meals`() {
        //given
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.now(),720f,"description1"),
            createMeal(2200,"name2", LocalDate.now(),710f,"description2"),
            createMeal(2300,"name3", LocalDate.now(),800f,"description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(mutableSetOf())).isIn(data)
    }

    @Test
    fun `getMeal should not contain all high calorie meals when there is unliked meals`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100)
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.now(),720f,"description1"),
            createMeal(2200,"name2", LocalDate.now(),710f,"description2"),
            createMeal(2300,"name3", LocalDate.now(),800f,"description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList)).isIn(mutableListOf(
            createMeal(2200,"name2", LocalDate.now(),710f,"description2"),
            createMeal(2300,"name3", LocalDate.now(),800f,"description3")
        ))
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when all meals is being unliked`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100,2200,2300)
        val data = mutableListOf(
            createMeal(2100,"name1", LocalDate.now(),720f,"description1"),
            createMeal(2200,"name2", LocalDate.now(),710f,"description2"),
            createMeal(2300,"name3", LocalDate.now(),800f,"description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(unLikedList)
        }
    }

    private fun createMeal(
        id: Int,
        name: String?,
        submittedDate: LocalDate?,
        calories: Float,
        description: String?,
    ): Food {
        return Food(
            id,
            name,
            minutes = 3,
            submittedDate,
            tags = listOf(),
            nutrition = Nutrition(calories, totalFat = 0f, sugar = 0f, sodium = 0f, protein = 0f, saturated = 0f, carbohydrates = 0f),
            steps = listOf(),
            description,
            ingredients = listOf()
        )
    }

}