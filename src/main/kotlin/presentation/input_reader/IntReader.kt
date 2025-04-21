package presentation.input_reader

open class IntReader: InputReader<Int> {
    override fun read(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}