package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GymHelperUseCase
import model.NoMealsFoundException
import model.WrongInputException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GymHelperConsoleUITest {
    private lateinit var gymHelperUseCase: GymHelperUseCase
    private val reader: InputReader = mockk(relaxed = true)
    private val consolePrinter: Printer = mockk(relaxed = true)
    private lateinit var gymHelperConsoleUI: GymHelperConsoleUI

    @BeforeEach
    fun setUp() {
        gymHelperUseCase = mockk(relaxed = true)
        gymHelperConsoleUI = GymHelperConsoleUI(gymHelperUseCase, reader, consolePrinter)
    }

    @Test
    fun `launch GymHelperConsoleUI should prints list of meals when enter valid calories and proteins`() {
        // Input
        val calories = 149F
        val proteins = 17F
        every { reader.readFloat() } returns calories andThen proteins
        every { gymHelperUseCase.getListOfMealsForGym(calories, proteins) } returns listOf(
            createMeal(name = "Meal 4", calories = 148.0F, protein = 17.0F),
            createMeal(name = "Meal 5", calories = 150.0F, protein = 18.0F),
        )

        // When
        gymHelperConsoleUI.launchUI()

        // Then
        verify(exactly = 1) {
            gymHelperUseCase.getListOfMealsForGym(calories, proteins)
            consolePrinter.displayLn(match {
                it.toString().contains(createMeal(name = "Meal 4", calories = 148.0F, protein = 17.0F).getFullDetails())
            })
            consolePrinter.displayLn(match {
                it.toString().contains(createMeal(name = "Meal 5", calories = 150.0F, protein = 18.0F).getFullDetails())
            })
        }
    }

    @Test
    fun `launch GymHelperConsoleUI should prints message of NoMealsFoundException when getListOfMealsForGym throws NoMealsFoundException`() {
        // Input
        val calories = 1149F
        val proteins = 17F
        every { reader.readFloat() } returns calories andThen proteins
        every { gymHelperUseCase.getListOfMealsForGym(calories, proteins) } throws NoMealsFoundException()

        // When
        gymHelperConsoleUI.launchUI()

        // Then
        verify(exactly = 1) {
            gymHelperUseCase.getListOfMealsForGym(calories, proteins)
            consolePrinter.displayLn(NoMealsFoundException().message)
        }
    }

    @Test
    fun `launch GymHelperConsoleUI should prints error when getListOfMealsForGym throws any Exception`() {
        // Input
        val calories = 1149F
        val proteins = 17F
        every { reader.readFloat() } returns calories andThen proteins
        every { gymHelperUseCase.getListOfMealsForGym(calories, proteins) } throws Exception()

        // When
        gymHelperConsoleUI.launchUI()

        // Then
        verify(exactly = 1) {
            gymHelperUseCase.getListOfMealsForGym(any(), any())
        }
    }

    @ParameterizedTest
    @CsvSource(
        ", 10.0",
        "a15, 10",
        "15-1, 10",
    )
    fun `launch GymHelperConsoleUI should prints message of WrongInputException when enter invalid input for calories`(
        calories: String?,
        proteins: String?,
    ) {
        // Input
        every { reader.readFloat() } returns calories?.toFloatOrNull() andThen proteins?.toFloatOrNull()

        // When
        gymHelperConsoleUI.launchUI()

        // Then
        verify(exactly = 0) { gymHelperUseCase.getListOfMealsForGym(any(), any()) }
        verify(exactly = 1) { consolePrinter.displayLn(WrongInputException().message) }
    }

    @ParameterizedTest
    @CsvSource(
        "10.0, ",
        "10, a15",
        "10, 15-1",
    )
    fun `launch GymHelperConsoleUI should prints message of WrongInputException when enter invalid input for proteins`(
        calories: String?,
        proteins: String?,
    ) {
        // Input
        every { reader.readFloat() } returns calories?.toFloatOrNull() andThen proteins?.toFloatOrNull()

        // When
        gymHelperConsoleUI.launchUI()

        // Then
        verify(exactly = 0) { gymHelperUseCase.getListOfMealsForGym(any(), any()) }
        verify(exactly = 1) { consolePrinter.displayLn(WrongInputException().message) }
    }
}