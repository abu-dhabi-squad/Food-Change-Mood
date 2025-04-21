package presentation.input_reader

interface InputReader<T> {
    fun read(): T?
}