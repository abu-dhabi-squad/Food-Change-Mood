package presentation.input_reader

open class StringReader:InputReader<String> {
    override fun read(): String? {
        return readlnOrNull()
    }
}