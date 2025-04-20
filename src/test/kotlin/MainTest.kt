import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun checkSum() {
        val result = sum(2, 4)
        assertEquals(8, result)
    }
}