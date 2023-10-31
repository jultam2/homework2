import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void testConstructorWithNullHorses() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void testConstructorWithNullHorsesMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructorWithEmptyHorses() {
        List<Horse> emptyList = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyList));
    }

    @Test
    public void testConstructorWithEmptyHorsesMessage() {
        List<Horse> emptyList = new ArrayList<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyList));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void testGetHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse " + i, 10.0, 0.0));
        }
        List<Horse> returnedHorses = new Hippodrome(horseList).getHorses();
        for (int i = 0; i < horseList.size(); i++) {
            assertEquals(horseList.get(i), returnedHorses.get(i));
        }
    }

    @Test
    public void testMoveCallsMoveOnHorses() {
        List<Horse> mockedHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            mockedHorses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();

        for (Horse horse : mockedHorses) {
            verify(horse, times(1)).move();
        }
    }

    @Test
    public void testGetWinner() {
        Horse winningHorse = new Horse("Winner", 15.0, 100.0);
        List<Horse> horseList = new ArrayList<>();
        horseList.add(new Horse("Horse1", 10.0, 50.0));
        horseList.add(new Horse("Horse2", 12.0, 60.0));
        horseList.add(winningHorse);

        Hippodrome hippodrome = new Hippodrome(horseList);
        Horse winner = hippodrome.getWinner();

        assertSame(winningHorse, winner);
    }
}