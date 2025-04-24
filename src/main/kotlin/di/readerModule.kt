package di

import org.koin.dsl.module
import presentation.ui_io.ConsoleReader
import presentation.ui_io.InputReader


val readerModule = module {
    single<InputReader> { ConsoleReader() }
}