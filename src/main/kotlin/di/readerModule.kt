package di

import org.koin.dsl.module
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.FloatReader
import presentation.ui_io.IntReader
import presentation.ui_io.StringReader

val readerModule = module{
    single { IntReader() }
    single { StringReader() }
    single { FloatReader() }
}
