package presentation

import presentation.ui_io.Printer

class PrinterForTest : Printer {
    private var displayLnInputs = ArrayList<Any?>()
    private var displayInputs = ArrayList<Any?>()

    override fun display(input: Any?) {
        displayInputs.add(input)
    }

    override fun displayLn(input: Any?) {
        displayLnInputs.add(input)

    }

    fun getDisplayInput(index : Int) : Any?{
        return displayInputs[index]
    }
    fun getDisplayLnInput(index : Int) : Any?{
        return displayLnInputs[index]
    }
}

