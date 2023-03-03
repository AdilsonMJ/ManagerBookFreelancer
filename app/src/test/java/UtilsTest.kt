import com.example.managerbookfreelancer.utils.Utils
import org.junit.Assert.*
import org.junit.Test

class UtilsTest{

    private val FAKE_DATE = 2187648000000L // 29/04/2039

    @Test
    fun pastLongAndWaitForString(){

        val date_string = "29/04/2039"

        val date = Utils.formatDate(FAKE_DATE)

        assertEquals(date, date_string)

    }

}