package presentation

import io.mockk.*
import logic.repository.FoodRepository
import logic.usecase.GetRandomKetoDietMealsUseCase
import logic.usecase.createMeal
import model.Food
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import presentation.ui_io.Printer
import kotlin.test.assertEquals

class RandomKetoMealUITest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase
    private lateinit var randomKetoMealUI: RandomKetoMealUI
    private val consolePrinter: Printer = mockk(relaxed = true)
    private val getUserTaste: GetUserTaste = mockk()

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        getRandomKetoDietMealsUseCase = GetRandomKetoDietMealsUseCase(foodRepository)
        randomKetoMealUI = RandomKetoMealUI(
            getRandomKetoDietMealsUseCase,
            getUserTaste,
            consolePrinter
        )
    }




}