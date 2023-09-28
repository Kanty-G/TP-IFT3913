
import org.junit.Test;
import static org.junit.Assert.*;



public class AssertTest {

    @Test
    public void testAssertions() {
        // Define some variables for testing
        int a = 5;
        int b = 10;
        String str1 = "Hello";
        String str2 = "World";
        String str3 = null;

        // Numeric Assertions
        assertEquals(5, a); // Assert that 'a' is equal to 5
        assertNotEquals(5, b); // Assert that 'b' is not equal to 5
        assertTrue(b > a); // Assert that 'b' is greater than 'a'
        assertFalse(a > b); // Assert that 'a' is not greater than 'b'

        // String Assertions
        assertEquals("Hello", str1); // Assert that 'str1' is equal to "Hello"
        assertNotEquals("Hi", str1); // Assert that 'str1' is not equal to "Hi"
        assertNull(str3); // Assert that 'str3' is null
        assertNotNull(str2); // Assert that 'str2' is not null
        assertSame(str1, "Hello"); // Assert that 'str1' and "Hello" refer to the same object
        assertNotSame(str1, str2); // Assert that 'str1' and 'str2' do not refer to the same object
    }
}
