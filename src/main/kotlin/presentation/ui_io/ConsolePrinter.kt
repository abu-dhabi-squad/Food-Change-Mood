package presentation.ui_io

class ConsolePrinter:Printer {
    override fun print(input: Any) {
        kotlin.io.print(input)
    }

    override fun println(input: Any) {
        kotlin.io.println(input)
    }
}