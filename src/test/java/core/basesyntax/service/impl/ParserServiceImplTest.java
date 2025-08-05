package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
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

        assertEquals(2, result.size());

        FruitTransaction first = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals("banana", first.getFruit());
        assertEquals(20, first.getQuantity());

        FruitTransaction second = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        assertEquals("apple", second.getFruit());
        assertEquals(10, second.getQuantity());
    }

    @Test
    void parse_emptyList_returnsEmpty() {
        List<FruitTransaction> result = parserService.parse(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_onlyHeader_returnsEmpty() {
        List<FruitTransaction> result = parserService.parse(List.of("type,fruit,quantity"));
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_invalidLine_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,not_a_number"
        );

        assertThrows(IllegalArgumentException.class, () ->
                parserService.parse(input)
        );
    }
}
