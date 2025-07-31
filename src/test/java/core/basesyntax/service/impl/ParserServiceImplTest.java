package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private final ParserService parserService = new ParserServiceImpl();

    @Test
    void parse_validInput_returnsCorrectTransactions() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        List<FruitTransaction> result = parserService.parse(input);

        Assertions.assertEquals(2, result.size());

        FruitTransaction first = result.get(0);
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        Assertions.assertEquals("banana", first.getFruit());
        Assertions.assertEquals(20, first.getQuantity());

        FruitTransaction second = result.get(1);
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        Assertions.assertEquals("apple", second.getFruit());
        Assertions.assertEquals(10, second.getQuantity());
    }

    @Test
    void parse_emptyList_returnsEmpty() {
        List<FruitTransaction> result = parserService.parse(List.of());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void parse_onlyHeader_returnsEmpty() {
        List<FruitTransaction> result = parserService.parse(List.of("type,fruit,quantity"));
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void parse_invalidLine_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,not_a_number"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                parserService.parse(input)
        );
    }
}
