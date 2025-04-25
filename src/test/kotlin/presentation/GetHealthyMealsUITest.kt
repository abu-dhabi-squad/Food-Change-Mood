package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetHealthyMealsUseCase
import model.EmptyHealthFoodListListException
import model.Food
import model.Nutrition
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GetHealthyMealsUITest {
    private lateinit var getHealthyMealsUseCase: GetHealthyMealsUseCase
    private lateinit var ui: GetHealthyMealsUI
    private val testOutput = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        getHealthyMealsUseCase = mockk()
        ui = GetHealthyMealsUI(getHealthyMealsUseCase)
        System.setOut(PrintStream(testOutput))
    }

    @Test
    fun `should print healthy meals when data is available`() {
        // given
        val meals = listOf(
            Food(
                id = 1,
                name = "Healthy Salad",
                minutes = 10,
                submittedDate = null,
                tags = listOf("vegan", "low-fat"),
                nutrition = Nutrition(
                    calories = 200f, totalFat = 4f, sugar = 2f,
                    sodium = 100f, protein = 6f, saturated = 1f, carbohydrates = 10f
                ),
                steps = listOf("Chop veggies", "Mix"),
                description = "A fresh green salad.",
                ingredients = listOf("lettuce", "tomato")
            )
        )

        every { getHealthyMealsUseCase.fetchHealthyFastFoods() } returns meals

        // when
        ui.launchUI()

        // then
        val output = testOutput.toString().trim()
        assertThat(output).contains("Healthy meals")
        assertThat(output).contains("Healthy Salad")
        assertThat(output).contains("A fresh green salad.")
    }

    @Test
    fun `should print error message when exception occurs`() {
        every { getHealthyMealsUseCase.fetchHealthyFastFoods() } throws EmptyHealthFoodListListException()

        ui.launchUI()

        val output = testOutput.toString().trim()
        assertThat(output).contains("No healthy meals found")
    }
}