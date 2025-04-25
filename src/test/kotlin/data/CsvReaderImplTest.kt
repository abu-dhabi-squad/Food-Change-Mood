package data

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

class CsvReaderImplTest {

    @Test
    fun `should throw FileNotFoundException when csv file does not exist`() {
        val file = File("non_existing_file_12345.csv")
        val csvReader = CsvReaderImpl(file)

        // when && then
        assertThrows<FileNotFoundException> {
            csvReader.readLines().toList()
        }
    }


    @Test
    fun `should return non empty list when csv file is non empty`(@TempDir tempDir: Path) {
        // given
        val file = tempDir.resolve("food.csv").toFile()
        val fileContent = "abc"
        file.writeText(fileContent)
        val csvReader = CsvReaderImpl(file)

        // when
        val result = csvReader.readLines()
        // then
        assertThat(result.toList()).isEqualTo(listOf(fileContent))
    }


    @Test
    fun `should return empty list when csv file is empty`(@TempDir tempDir: Path) {
        // given
        val file = tempDir.resolve("food.csv").toFile()
        file.writeText("")
        val csvReader = CsvReaderImpl(file)
        // when
        val result = csvReader.readLines()

        // then
        assertThat(result.toList()).isEmpty()
    }

}