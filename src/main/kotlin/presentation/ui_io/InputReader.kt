package presentation.ui_io

interface InputReader<T> {
    fun read(): T?
}