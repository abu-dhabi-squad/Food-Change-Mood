package presentation.ui_io

open class StringReader:InputReader<String> {
    override fun read(): String? {
        return readlnOrNull()
    }
}