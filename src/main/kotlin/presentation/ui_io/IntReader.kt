package presentation.ui_io

open class IntReader: InputReader<Int> {
    override fun read(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}