package presentation

import io.mockk.mockk
import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import org.junit.jupiter.api.BeforeEach
import presentation.ui_io.IntReader
import presentation.ui_io.Printer
import presentation.ui_io.StringReader
import util.DateParserInterface
import util.DateValidationInterface

class GetFoodByDateUITest{

    private val getFoodByDateUseCase: GetFoodByDateUseCase = mockk(relaxed = true)
    private val getMealByIdUseCase: GetMealByIdUseCase = mockk(relaxed = true)
    private val dateParserInterface: DateParserInterface = mockk()
    private val dateValidationInterface: DateValidationInterface = mockk()
    private val intReader: IntReader = mockk()
    private val stringReader: StringReader = mockk()
    private val printer: Printer = mockk()
    private lateinit var getFoodByDateUI: GetFoodByDateUI

    @BeforeEach
    fun setUp(){
        getFoodByDateUI = GetFoodByDateUI(dateParserInterface,
            getFoodByDateUseCase,
            getMealByIdUseCase,
            dateValidationInterface,
            intReader,
            stringReader,
            printer)
    }



}