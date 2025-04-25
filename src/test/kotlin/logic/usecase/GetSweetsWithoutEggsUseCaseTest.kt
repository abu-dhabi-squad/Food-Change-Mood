package logic.usecase


import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.verify
import model.NoMealsFoundException
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GetSweetsWithoutEggsUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        getSweetsWithoutEggsUseCase = GetSweetsWithoutEggsUseCase(foodRepository)
    }

    // repo failure
    @Test
    fun `should throw exception when repository returns failure`() {
        //Given
        every { foodRepository.getFoods() } returns Result.failure(exception = NoMealsFoundException())

        // When and Then
        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(mutableSetOf())
        }
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    // list empty
    @Test
    fun `should throw NoMealsFoundException when food list is empty`() {
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(emptySet())
        }
    }

    //isValidMeal
    @Test
    fun `should throw NoMealsFoundException when all meals are invalid`() {
        val meals = listOf(
            createMeal(
                id = 4,
                name = null,
                tags = listOf("dessert", "fruit", "rr"),
                description = "dessert",
                ingredients = listOf("Apples", "egg", "Sugar")
            ),

            createMeal(
                id = 4,
                name = "Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = null,
                ingredients = listOf("Apples", "egg", "Sugar")
            ),

            createMeal(
                id = 4,
                name = "Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "",
                ingredients = listOf("Apples", "egg", "Sugar")
            ),

            createMeal(
                id = 4,
                name = "",
                tags = listOf("dessert", "fruit", "rr"),
                description = "ddd",
                ingredients = listOf("Apples", "egg", "Sugar")
            ),

            createMeal(
                id = 4,
                name = "Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "",
                ingredients = listOf("Apples", "egg", "Sugar")
            )
        )
        every { foodRepository.getFoods() } returns Result.success(meals)
        every { foodRepository.getFoods() } returns Result.success(meals)

        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(emptySet())
        }
    }

    // isSweet name
    @Test
    fun `should return valid meal when name contains sweet`() {
        // Given

        val validMeals = listOf(
            createMeal(
                id = 4,
                name = "sweet Apple Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "A very  and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(validMeals)
        val result = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(setOf(2, 3))

        assertThat(validMeals).containsExactly(result)
        verify(exactly = 1) {
            foodRepository.getFoods()
        }
    }

    // isSweet tags
    @Test
    fun `should return valid meal when tags contains sweet`() {
        // Given

        val validMeals = listOf(
            createMeal(
                id = 4,
                name = " Apple Pie",
                tags = listOf("dessert", "fruit", "sweet"),
                description = "A very and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )

        )

        every { foodRepository.getFoods() } returns Result.success(validMeals)
        val result = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(setOf(2, 3))

        assertThat(validMeals).containsExactly(result)
        verify(exactly = 1) {
            foodRepository.getFoods()
        }
    }

    // isSweet description
    @Test
    fun `should return valid meal when description contains sweet`() {
        // Given

        val validMeals = listOf(
            createMeal(
                id = 4,
                name = " Apple Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "A very sweet and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(validMeals)
        val result = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(setOf(2, 3))

        assertThat(validMeals).containsExactly(result)
        verify(exactly = 1) {
            foodRepository.getFoods()
        }
    }

    //isSweet
    @Test
    fun `should throw NoMealsFoundException when no meals are sweet`() {
        val meals = listOf(
            createMeal(
                id = 4,
                name = "Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "dessert",
                ingredients = listOf("Apples", "egg", "Sugar")
            )
        )
        every { foodRepository.getFoods() } returns Result.success(meals)

        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(emptySet())
        }
    }

    //isNotShownBefore
    @Test
    fun `should throw NoMealsFoundException when all meals are already shown`() {
        val meals = listOf(
            createMeal(
                id = 1,
                name = "Sweet Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "desc",
                ingredients = listOf("Apples", "Sugar")
            )
        )
        every { foodRepository.getFoods() } returns Result.success(meals)

        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(setOf(1))
        }
    }

    //isEggFree contain egg
    @Test
    fun `should throw NoMealsFoundException when all meals contain eggs`() {
        val meals = listOf(
            createMeal(
                id = 4,
                name = "Sweet Egg Pie",
                tags = listOf("dessert", "fruit", "rr"),
                description = "Eggy dessert",
                ingredients = listOf("Apples", "egg", "Sugar")
            )
        )
        every { foodRepository.getFoods() } returns Result.success(meals)
        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(emptySet())
        }
    }

    //isEggFree ingredients is empty
    @Test
    fun `should throw NoMealsFoundException when ingredients is empty`() {
        val meals = listOf(
            createMeal(
                id = 4,
                name = "Sweet Cake",
                tags = listOf("sweet", "dessert"),
                description = "Tasty sweet dessert",
                ingredients = emptyList()
            )

        )
        every { foodRepository.getFoods() } returns Result.success(meals)

        assertThrows<NoMealsFoundException> {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(emptySet())
        }
    }

    @Test
    fun `should return sweet meal without eggs and not shown before`() {
        val shownMeals = setOf(99, 100)
        val validSweetMeal = createMeal(
            id = 1,
            name = "Sweet Pie",
            tags = listOf("dessert", "fruit", "sweet"),
            description = "A delicious sweet pie",
            ingredients = listOf("Apples", "Sugar")
        )

        val invalidMealWithEgg = createMeal(
            id = 2,
            name = "Sweet Omelette",
            tags = listOf("breakfast", "sweet"),
            description = "Sweet omelette with eggs",
            ingredients = listOf("Egg", "Sugar")
        )

        val repo = mockk<FoodRepository>()
        every { repo.getFoods() } returns Result.success(listOf(validSweetMeal, invalidMealWithEgg))
        val useCase = GetSweetsWithoutEggsUseCase(repo)
        val result = useCase.getSweetsWithoutEggs(shownMeals)
        assertEquals(validSweetMeal, result)
    }
}




