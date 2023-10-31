import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class HorseTest {

    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10.0, 0.0));
    }

    @Test
    public void testConstructorWithNullNameMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10.0, 0.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n", "  \t  " })
    public void testConstructorWithBlankName(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0, 0.0));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n", "  \t  " })
    public void testConstructorWithBlankNameMessage(String name) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0, 0.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", -10.0, 0.0));
    }

    @Test
    public void testConstructorWithNegativeSpeedMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", -10.0, 0.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", 10.0, -100.0));
    }

    @Test
    public void testConstructorWithNegativeDistanceMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", 10.0, -100.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void testGetNameReturnsString() {
        String expectedName = "Bucephalus";
        Horse horse = new Horse(expectedName, 10.0, 0.0);
        String actualName = horse.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetSpeedReturnsDouble() {
        double expectedSpeed = 10.0;
        Horse horse = new Horse("Bucephalus", expectedSpeed, 0.0);
        double actualSpeed = horse.getSpeed();
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    public void testGetDistanceReturnsDouble() {
        double expectedDistance = 42.0;
        Horse horse = new Horse("Bucephalus", 10.0, expectedDistance);
        double actualDistance = horse.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void testGetDistanceWithTwoParameters() {
        Horse horse = new Horse("Bucephalus", 10.0);
        double actualDistance = horse.getDistance();
        assertEquals(0.0, actualDistance);
    }

    @Test
    public void testMoveCallsGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenCallRealMethod();
            Horse horse = new Horse("Bucephalus", 10.0, 0.0);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({ "0.2, 0.9, 0.5"})
    public void testMoveCalculatesDistance(double min, double max, double randomValue) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(randomValue);
            Horse horse = new Horse("Bucephalus", 10.0, 0.0);
            horse.move();
            double expectedDistance = 0.0 + 10.0 * randomValue;
            double actualDistance = horse.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }
}