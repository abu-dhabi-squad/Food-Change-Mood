package data

interface FileReader {
    fun readLines(): Sequence<String>
}