package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import io.mockk.verify
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
        val potatoMeals = listOf(
            createPotatoMeal(
                "tourtiere french canadian meat pie",
                ingredients = listOf("potatoes", "pickel")
            ),
            createPotatoMeal("potatoes with green sauce", ingredients = listOf("potatoes", "pickel")),
            createPotatoMeal(
                "cottage pie with kumara topping",
                ingredients = listOf("potatoes", "pickel")
            ),
            createPotatoMeal(
                "poor man s casserole potato egg bacon casserole",
                ingredients = listOf("potatoes", "pickel")
            ),
            createPotatoMeal("swiss steak supper crock pot", ingredients = listOf("potatoes", "pickel")),
            createPotatoMeal(
                "delicious oven baked beef stew",
                ingredients = listOf("potatoes", "pickel")
            ),
            createPotatoMeal("gauranga potatoes", ingredients = listOf("potatoes", "pickel")),
            createPotatoMeal("cilantro potato soup", ingredients = listOf("potatoes", "pickel")),
            createPotatoMeal("ham cheese baked potatoes", ingredients = listOf("potatoes", "pickel")),
            createPotatoMeal("sweet potato soup world class", ingredients = listOf("potatoes", "pickel"))
        )

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
            createPotatoMeal(null, ingredients = listOf("potatoes")),
            createPotatoMeal("", ingredients = listOf("potatoes")),
            createPotatoMeal(" ", ingredients = listOf("potatoes"))
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
            createPotatoMeal("Rice Dish", ingredients = listOf("rice", "chicken")),
            createPotatoMeal("Salad", ingredients = listOf("lettuce", "tomato"))
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
            createPotatoMeal("Potato Pie", ingredients = listOf("potatoes")),
            createPotatoMeal(null, ingredients = listOf("potatoes")),
            createPotatoMeal("Potato Salad", ingredients = listOf("lettuce", "tomato")),
            createPotatoMeal(" ", ingredients = listOf("potatoes")),
            createPotatoMeal("Mashed Potatoes", ingredients = listOf("potatoes", "butter"))
        )

        every { foodRepository.getFoods() } returns Result.success(meals)
        // When
        val result = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()
        // Then
        assertThat(result).containsExactlyElementsIn(
            listOf("Potato Pie", "Mashed Potatoes")
        )

    }
}