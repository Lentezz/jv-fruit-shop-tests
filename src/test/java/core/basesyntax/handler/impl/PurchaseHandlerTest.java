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

public class PurchaseHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @BeforeEach
    void init() {
        Storage.updateFruit("Apple", 10);
    }

    @Test
    public void apply_shouldDecreaseFruitQuantityInStorageIfEnough() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Apple", 6);
        purchaseHandler.apply(fruitTransaction);
        assertEquals(4, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldDecreaseFruitQuantityToZero() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Apple", 10);
        purchaseHandler.apply(fruitTransaction);
        assertEquals(0, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldThrowExceptionIfFruitQuantityInStorageIsNotEnough() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Apple", 15);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.apply(fruitTransaction));
    }

    @Test
    public void apply_shouldThrowExceptionIfFruitIsNotExistInStorage() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 1);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.apply(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
