package core.basesyntax.service.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FruitServiceImplTest {
    private OperationStrategy strategyMock;
    private OperationHandler handlerMock;
    private FruitService fruitService;

    @BeforeEach
    void setUp() {
        strategyMock = Mockito.mock(OperationStrategy.class);
        handlerMock = Mockito.mock(OperationHandler.class);
        fruitService = new FruitServiceImpl(strategyMock);
    }

    @Test
    void process_validTransactions_handlersApplied() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Banana", 10);
        Mockito.when(strategyMock
                .getHandler(FruitTransaction.Operation.PURCHASE))
                .thenReturn(handlerMock);
        fruitService.process(List.of(transaction));
        Mockito.verify(strategyMock, Mockito.times(1))
                .getHandler(FruitTransaction.Operation.PURCHASE);
        Mockito.verify(handlerMock, Mockito.times(1))
                .apply(transaction);
    }
}
