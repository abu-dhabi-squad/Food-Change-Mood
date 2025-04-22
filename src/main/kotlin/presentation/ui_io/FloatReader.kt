package presentation.ui_io

open class FloatReader: InputReader<Float> {
    override fun read(): Float? {
        return readlnOrNull()?.toFloatOrNull()
    }
}