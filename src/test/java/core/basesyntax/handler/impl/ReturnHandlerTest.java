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

public class ReturnHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeAll
    public static void setUp() {
        returnHandler = new ReturnHandler();
    }

    @BeforeEach
    public void beforeEach() {
        Storage.updateFruit("Apple", 10);
    }

    @Test
    public void apply_shouldIncreaseFruitQuantity() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 15);
        returnHandler.apply(fruitTransaction);
        assertEquals(25, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldNotIncreaseFruitQuantityIfZero() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 0);
        returnHandler.apply(fruitTransaction);
        assertEquals(10, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldThrowExceptionIfNegative() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", -1);
        assertThrows(IllegalArgumentException.class,
                () -> returnHandler.apply(fruitTransaction));
    }

    @Test
    public void apply_shouldAddFruitToStorageIfNotExist() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Banana", 4);
        returnHandler.apply(fruitTransaction);
        assertEquals(4, Storage.getFruitQuantity("Banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
