package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @BeforeEach
    void init() {
        Storage.clear();
    }

    @Test
    public void apply_shouldSetNewBalanceForExistingFruit() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Banana", 44);
        Storage.updateFruit("Banana", 55);
        balanceHandler.apply(fruitTransaction);
        int actual = Storage.getFruitQuantity(fruitTransaction.getFruit());
        Assertions.assertEquals(44, actual);
    }

    @Test
    public void apply_shouldAddUnknownFruitToStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 2);
        balanceHandler.apply(fruitTransaction);
        Assertions.assertEquals(2, Storage
                .getFruitQuantity(fruitTransaction.getFruit()));
    }
}
