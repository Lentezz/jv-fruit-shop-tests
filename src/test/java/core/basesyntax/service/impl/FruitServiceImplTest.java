package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceHandler;
import core.basesyntax.handler.impl.PurchaseHandler;
import core.basesyntax.handler.impl.ReturnHandler;
import core.basesyntax.handler.impl.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private FruitService fruitService;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> handlerMap = Map.of(
                Operation.BALANCE, new BalanceHandler(),
                Operation.PURCHASE, new PurchaseHandler(),
                Operation.RETURN, new ReturnHandler(),
                Operation.SUPPLY, new SupplyHandler());
        OperationStrategy strategy = new OperationStrategyImpl(handlerMap);
        fruitService = new FruitServiceImpl(strategy);
    }

    @Test
    void process_allOperations_correctlyUpdatesStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 30),
                new FruitTransaction(Operation.RETURN, "banana", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 20));
        fruitService.process(transactions);
        assertEquals(100, Storage.getFruitQuantity("banana"));
    }

    @Test
    void process_purchaseMoreThanAvailable_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 15));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fruitService.process(transactions));
        assertTrue(exception.getMessage().contains("Not enough stock for: apple"));
    }

    @Test
    void process_negativeReturn_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 50),
                new FruitTransaction(Operation.RETURN, "banana", -5));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fruitService.process(transactions));
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void process_negativeSupply_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 50),
                new FruitTransaction(Operation.SUPPLY, "banana", -7));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fruitService.process(transactions));
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
