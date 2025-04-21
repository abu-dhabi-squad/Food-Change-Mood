package presentation.input_reader

open class FloatReader: InputReader<Float> {
    override fun read(): Float? {
        return readlnOrNull()?.toFloatOrNull()
    }
}