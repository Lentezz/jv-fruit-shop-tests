package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
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
        Storage.clear();
        Storage.updateFruit("Apple", 10);
    }

    @Test
    public void apply_shouldIncreaseFruitQuantityIfFruitIsExist() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 15);
        supplyHandler.apply(fruitTransaction);
        Assertions.assertEquals(25, Storage.getFruitQuantity("Apple"));

    }

    @Test
    public void apply_shouldNotIncreaseFruitQuantityIfZero() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 0);
        supplyHandler.apply(fruitTransaction);
        Assertions.assertEquals(10, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void apply_shouldThrowExceptionIfNegative() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", -1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> supplyHandler.apply(fruitTransaction));
    }

    @Test
    public void apply_shouldAddFruitToStorageIfNotExist() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Banana", 4);
        supplyHandler.apply(fruitTransaction);
        Assertions.assertEquals(4, Storage.getFruitQuantity("Banana"));
    }
}
