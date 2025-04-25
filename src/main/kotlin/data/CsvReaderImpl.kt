package data

import java.io.File
import java.io.FileNotFoundException

class CsvReaderImpl(private val file: File)  : FileReader{

    override fun readLines(): Sequence<String> = sequence {
        if (!file.exists()) throw FileNotFoundException("File does not exist")

        file.bufferedReader().useLines { lines ->
            for (line in lines) {
                yield(line)
            }
        }
    }


}

