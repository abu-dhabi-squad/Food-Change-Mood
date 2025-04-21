package di

import org.koin.dsl.module
import presentation.input_reader.FloatReader
import presentation.input_reader.IntReader
import presentation.input_reader.StringReader

val readerModule = module{
    single { IntReader() }
    single { StringReader() }
    single { FloatReader() }
}
