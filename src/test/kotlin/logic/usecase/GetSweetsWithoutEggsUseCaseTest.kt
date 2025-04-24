package logic.usecase


import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.verify
import model.Food
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import kotlin.test.assertEquals

class GetSweetsWithoutEggsUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        getSweetsWithoutEggsUseCase = GetSweetsWithoutEggsUseCase(foodRepository)
    }
    // repo failue
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
            Food(
                id = 4,
                name = null,
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "dessert",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
            ),
            Food(
                id = 4,
                name = "Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = null,
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
            ),
            Food(
                id = 4,
                name = "Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
            ),
            Food(
                id = 4,
                name = "",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "ddd",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
            ),
            Food(
                id = 4,
                name = "Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
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
            Food(
                id = 4,
                name = "sweet Apple Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "A very  and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter") // بدون بيض
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
            Food(
                id = 4,
                name = " Apple Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "sweet"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "A very  and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter") // بدون بيض
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
            Food(
                id = 4,
                name = " Apple Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "A very sweet and tasty apple pie.",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter") // بدون بيض
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
            Food(
                id = 4,
                name = "Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "dessert",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
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
            Food(
                id = 1,
                name = "Sweet Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice sweet apples", "Bake the pie"),
                description = "desc",
                ingredients = listOf("Apples", "Sugar") // بدون بيض
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
            Food(
                id = 4,
                name = "Sweet Egg Pie",
                minutes = 45,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("dessert", "fruit", "rr"),
                nutrition = Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Slice apples", "Bake the pie"),
                description = "Eggy dessert",
                ingredients = listOf("Apples", "egg", "Sugar") // بدون بيض
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
            Food(
                id = 4,
                name = "Sweet Cake",
                minutes = 30,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("sweet", "dessert"),
                nutrition = Nutrition(300f, 10f, 20f, 50f, 5f, 2f, 40f),
                steps = listOf("Mix ingredients", "Bake"),
                description = "Tasty sweet dessert",
                ingredients = emptyList() // فارغة
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
        val validSweetMeal = Food(id = 1, name = "Sweet Pie", minutes = 45, submittedDate = LocalDate.of(2023, 5, 1),
            tags = listOf("dessert", "fruit", "sweet"), // تحتوي على sweet
            nutrition = Nutrition(
                calories = 300f,
                totalFat = 15f,
                sugar = 25f,
                sodium = 100f,
                protein = 3f,
                saturated = 5f,
                carbohydrates = 40f
            ),
            steps = listOf("Slice sweet apples", "Bake the pie"),
            description = "A delicious sweet pie",
            ingredients = listOf("Apples", "Sugar") // بدون بيض
        )
        val invalidMealWithEgg = Food(
            id = 2,
            name = "Sweet Omelette",
            minutes = 10,
            submittedDate = LocalDate.of(2023, 5, 2),
            tags = listOf("breakfast", "sweet"),
            nutrition = Nutrition(
                calories = 150f,
                totalFat = 10f,
                sugar = 10f,
                sodium = 200f,
                protein = 6f,
                saturated = 2f,
                carbohydrates = 15f
            ),
            steps = listOf("Mix eggs and sugar", "Cook on pan"),
            description = "Sweet omelette with eggs",
            ingredients = listOf("Egg", "Sugar") // يحتوي على بيض
        )
        val repo = mockk<FoodRepository>()
        every { repo.getFoods() } returns Result.success(listOf(validSweetMeal, invalidMealWithEgg))
        val useCase = GetSweetsWithoutEggsUseCase(repo)
        val result = useCase.getSweetsWithoutEggs(shownMeals)
        assertEquals(validSweetMeal, result)
    }
}




