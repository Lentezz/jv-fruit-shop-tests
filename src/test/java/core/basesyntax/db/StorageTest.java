package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    public void setUp() {
        Storage.updateFruit("Apple", 20);
        Storage.updateFruit("Orange", 30);
        Storage.updateFruit("Watermelon", 40);
    }

    @Test
    public void updateFruit_shouldOverwritePreviousValue() {
        String fruitName = "Apple";
        Storage.updateFruit(fruitName, 30);
        int expected = 30;
        int actual = Storage.getAll().get(fruitName);
        assertEquals(expected, actual);
    }

    @Test
    public void getFruitQuantity_shouldReturnCorrectValueForExistingFruits() {
        assertEquals(20, Storage.getFruitQuantity("Apple"));
        assertEquals(30, Storage.getFruitQuantity("Orange"));
        assertEquals(40, Storage.getFruitQuantity("Watermelon"));
    }

    @Test
    public void getFruitQuantity_shouldReturnZeroValueForNonExistingFruits() {
        assertEquals(0, Storage.getFruitQuantity("Unknown fruit"));
    }

    @Test
    public void getAll_shouldReturnAllInsertedFruits() {
        Map<String, Integer> allFruits = Storage.getAll();
        assertEquals(3, allFruits.size());
        Storage.updateFruit("Banana", 100);
        allFruits = Storage.getAll();
        assertEquals(4, allFruits.size());
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }
}
