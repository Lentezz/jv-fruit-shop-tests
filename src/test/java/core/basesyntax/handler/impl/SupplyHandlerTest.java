package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeAll
    public static void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @BeforeEach
    public void init() {
        Storage.updateFruit("Apple", 10);
    }

    @Test
    public void apply_shouldIncreaseFruitQuantityIfFruitIsExist() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 15);
        supplyHandler.apply(fruitTransaction);
        assertEquals(25, Storage.getFruitQuantity("Apple"));

    }

    @Test
    public void apply_shouldNotIncreaseFruitQuantityIfZero() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 0);
        supplyHandler.apply(fruitTransaction);
        assertEquals(10, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldThrowExceptionIfNegative() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", -1);
        assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.apply(fruitTransaction));
    }

    @Test
    public void apply_shouldAddFruitToStorageIfNotExist() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Banana", 4);
        supplyHandler.apply(fruitTransaction);
        assertEquals(4, Storage.getFruitQuantity("Banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
