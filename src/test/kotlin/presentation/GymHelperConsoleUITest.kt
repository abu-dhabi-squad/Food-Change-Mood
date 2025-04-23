package presentation

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import logic.usecase.GymHelperUseCase
import logic.usecase.createMealForGymHelper
import model.NoMealsFoundException
import model.WrongInputException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import presentation.ui_io.FloatReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GymHelperConsoleUITest {
    private lateinit var gymHelperUseCase: GymHelperUseCase
    private val floatReader: FloatReader = mockk(relaxed = true)
    private lateinit var gymHelperConsoleUI: GymHelperConsoleUI

    private val originalOut = System.out
    private lateinit var outputStream: ByteArrayOutputStream

    @BeforeEach
    fun setUp() {
        gymHelperUseCase = mockk(relaxed = true)
        gymHelperConsoleUI = GymHelperConsoleUI(gymHelperUseCase, floatReader)
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `launch GymHelperConsoleUI should prints list of meals when enter valid calories and proteins`() {
        // Input
        val calories = 149F
        val proteins = 17F
        every { floatReader.read() } returns calories andThen proteins
        every { gymHelperUseCase.getListOfMealsForGym(calories, proteins) } returns listOf(
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
        )

        // When
        gymHelperConsoleUI.launchUI()
        val printed = outputStream.toString().trim()

        // Then
        listOf(
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
        ).forEach {
            Truth.assertThat(printed).contains(it.getFullDetails())
        }
    }

    @Test
    fun `launch GymHelperConsoleUI should prints message of NoMealsFoundException when getListOfMealsForGym throws NoMealsFoundException`() {
        // Input
        val calories = 1149F
        val proteins = 17F
        every { floatReader.read() } returns calories andThen proteins
        every { gymHelperUseCase.getListOfMealsForGym(calories, proteins) } throws NoMealsFoundException()

        // When
        gymHelperConsoleUI.launchUI()
        val printed = outputStream.toString().trim()

        // Then
        Truth.assertThat(printed).contains(NoMealsFoundException().message)
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
        every { floatReader.read() } returns calories?.toFloatOrNull() andThen proteins?.toFloatOrNull()

        // When
        gymHelperConsoleUI.launchUI()
        val printed = outputStream.toString().trim()

        // Then
        Truth.assertThat(printed).contains(WrongInputException().message)
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
        every { floatReader.read() } returns calories?.toFloatOrNull() andThen proteins?.toFloatOrNull()

        // When
        gymHelperConsoleUI.launchUI()
        val printed = outputStream.toString().trim()

        // Then
        Truth.assertThat(printed).contains(WrongInputException().message)
    }
}