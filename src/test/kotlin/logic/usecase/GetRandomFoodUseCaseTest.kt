package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.Food
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

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
        val appleFood =Food(
            id = 1,
            name = "Apple Pie",
            minutes = 45,
            submittedDate = LocalDate.of(2023, 5, 1),
            tags = listOf("dessert", "fruit"),
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
            description = "A tasty apple pie.",
            ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
        )

        val chickenFood =   Food(
            id = 2,
            name = "Grilled Chicken",
            minutes = 30,
            submittedDate = LocalDate.of(2023, 6, 15),
            tags = listOf("main course", "protein"),
            nutrition = Nutrition(
                calories = 220f,
                totalFat = 6f,
                sugar = 0f,
                sodium = 70f,
                protein = 35f,
                saturated = 1f,
                carbohydrates = 2f
            ),
            steps = listOf("Season chicken", "Grill until done"),
            description = "Healthy grilled chicken.",
            ingredients = listOf("Chicken breast", "Spices")
        )
        val saladFood = Food(
            id = 3,
            name = "Greek Salad",
            minutes = 15,
            submittedDate = LocalDate.of(2023, 7, 10),
            tags = listOf("vegetarian", "salad"),
            nutrition = Nutrition(
                calories = 150f,
                totalFat = 10f,
                sugar = 3f,
                sodium = 200f,
                protein = 5f,
                saturated = 2f,
                carbohydrates = 10f
            ),
            steps = listOf("Chop veggies", "Mix ingredients"),
            description = "A refreshing Greek salad.",
            ingredients = listOf("Tomatoes", "Cucumber", "Feta cheese", "Olives")
        )

       val foods =  listOf(appleFood,chickenFood,saladFood)
        every { foodRepository.getFoods() } returns  Result.success(foods)

        val food = getRandomFoodUseCase.invoke()

        assertThat(foods).contains(food)
        verify (exactly = 1){ foodRepository.getFoods() }

    }

    @Test
    fun `should throw exception when food repository return  failure`(){
         every { foodRepository.getFoods() } returns  Result.failure(Exception())

        assertThrows<Exception>  {
            getRandomFoodUseCase.invoke()
        }
    }


}