package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import logic.usecase.createFood
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.IntReader
import presentation.ui_io.Printer
import java.nio.CharBuffer


class GuessFoodPreparationTimeGameUITest {
   private lateinit var guessFoodPreparationTimeGameUI : GuessFoodPreparationTimeGameUI
    private lateinit var  getRandomFoodUseCase :GetRandomFoodUseCase
    private lateinit var  guessFoodPreparationTimeUseCase : GuessFoodPreparationTimeUseCase
    private  var intReader: IntReaderForTest = IntReaderForTest()
    private  var printer: PrinterForTest = PrinterForTest()
    @BeforeEach
    fun setup(){
        getRandomFoodUseCase = mockk(relaxed = true)
        guessFoodPreparationTimeUseCase  = mockk(relaxed = true)

        guessFoodPreparationTimeGameUI = GuessFoodPreparationTimeGameUI(getRandomFoodUseCase, guessFoodPreparationTimeUseCase, intReader, printer)
    }

    @Test
    fun `launchUI should show food name when get random food use case return food object`(){
        // given
        val food = createFood(name = "chicken" , minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        // when
        intReader.readInput = 5
        guessFoodPreparationTimeGameUI.launchUI()
        // then
        assertThat(printer.getDisplayLnInput(0).toString()).contains(food.name)

    }


    @Test
    fun `launchUI should show exception when get random food use case fail`(){
        // given
        val exceptionMessage = "can not get food"
        every { getRandomFoodUseCase.invoke() } throws  Exception(exceptionMessage)
        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        assertThat(printer.getDisplayLnInput(0).toString()).contains(exceptionMessage)
    }

}


class PrinterForTest : Printer{
   private var displayLnInputs = ArrayList<Any>()
    private var displayInputs = ArrayList<Any>()

    override fun display(input: Any) {
        displayInputs.add(input)
    }

    override fun displayLn(input: Any) {
        displayLnInputs.add(input)

    }

    fun getDisplayInput(index : Int) : Any{
        return displayInputs
    }
    fun getDisplayLnInput(index : Int) : Any{
        return displayLnInputs
    }
}

class IntReaderForTest : InputReader<Int> {

   var readInput : Int? = null
    override fun read(): Int? {
        return readInput
    }

}