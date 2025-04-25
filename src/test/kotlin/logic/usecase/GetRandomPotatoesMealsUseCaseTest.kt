package logic.usecase

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import io.mockk.verify
import model.Food
import org.junit.jupiter.api.assertThrows

class GetRandomPotatoesMealsUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getRandomPotatoesMealsUseCase: GetRandomPotatoesMealsUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk()
        getRandomPotatoesMealsUseCase = GetRandomPotatoesMealsUseCase(foodRepository)
    }

    @Test
    fun `should throw exception when repository returns failure`() {
        // Given
        val exception = Exception("Failed to fetch foods")
        every { foodRepository.getFoods() } returns Result.failure(exception)

        // When , Then
        assertThrows<Exception> {
            getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()
        }

        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return ten meals when food repository returns success  if more are available`() {
        // Given
        val potatoMeals = getTenMealsContainsPotatoes()

        every { foodRepository.getFoods() } returns Result.success(potatoMeals)

        // When
        val result = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()
        val excepted = potatoMeals.map { it.name }
        // Then
        assertThat(result).containsExactlyElementsIn(excepted)
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should skip meals with blank or null names`() {
        // Given
        val potatoMeals = listOf(
            createMeal(name = null, ingredients = listOf("potatoes")),
            createMeal(name = "", ingredients = listOf("potatoes")),
            createMeal(name = " ", ingredients = listOf("potatoes"))
        )

        every { foodRepository.getFoods() } returns Result.success(potatoMeals)

        // When
        val result = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()

        // Then
        assertThat(result).isEmpty()
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should skip meals that do not contain potatoes in ingredients`() {
        // Given
        val potatoMeals = listOf(
            createMeal(name = "Rice Dish", ingredients = listOf("rice", "chicken")),
            createMeal(name = "Salad", ingredients = listOf("lettuce", "tomato"))
        )

        every { foodRepository.getFoods() } returns Result.success(potatoMeals)
        // When
        val result = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()
        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return only valid meals with name and potatoes ingredient`() {
        // Given
        val meals = listOf(
            createMeal(name = "Potato Pie", ingredients = listOf("potatoes")),
            createMeal(name = null, ingredients = listOf("potatoes")),
            createMeal(name = "Potato Salad", ingredients = listOf("lettuce", "tomato")),
            createMeal(name = " ", ingredients = listOf("potatoes")),
            createMeal(name = "Mashed Potatoes", ingredients = listOf("potatoes", "butter"))
        )

        every { foodRepository.getFoods() } returns Result.success(meals)
        // When
        val result = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()
        // Then
        assertThat(result).containsExactlyElementsIn(
            listOf("Potato Pie", "Mashed Potatoes")
        )

    }

    private fun getTenMealsContainsPotatoes(): List<Food> {
        return listOf(
            createMeal(
                name = "tourtiere french canadian meat pie",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "potatoes with green sauce",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "cottage pie with kumara topping",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "poor man s casserole potato egg bacon casserole",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "swiss steak supper crock pot",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "delicious oven baked beef stew",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(name = "gauranga potatoes", ingredients = listOf("potatoes", "pickel")),
            createMeal(name = "cilantro potato soup", ingredients = listOf("potatoes", "pickel")),
            createMeal(
                name = "ham cheese baked potatoes",
                ingredients = listOf("potatoes", "pickel")
            ),
            createMeal(
                name = "sweet potato soup world class",
                ingredients = listOf("potatoes", "pickel")
            )
        )
    }
}