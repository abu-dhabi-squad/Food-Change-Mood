package di

import org.koin.dsl.module
import presentation.ui_io.FloatReader
import presentation.ui_io.InputReader
import presentation.ui_io.IntReader
import presentation.ui_io.StringReader

val readerModule = module{
    single<InputReader<Int>> { IntReader() }
    single { StringReader() }
    single <InputReader<Float>>{ FloatReader()  }

}
