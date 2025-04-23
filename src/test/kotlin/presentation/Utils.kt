package presentation

import java.io.ByteArrayOutputStream
import java.io.PrintStream

fun captureOutput(block: () -> Unit): String {
    val originalOut = System.out
    val outputStream = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStream))

    return try {
        block()
        outputStream.toString().trim()
    } finally {
        System.setOut(originalOut)
    }
}