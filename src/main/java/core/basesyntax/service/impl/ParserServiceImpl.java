package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_CODE_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            String[] parts = data.get(i).split(SEPARATOR);
            FruitTransaction.Operation operation = FruitTransaction
                    .Operation
                    .fromCode(parts[OPERATION_CODE_INDEX]);
            transactions.add(new FruitTransaction(operation,
                    parts[FRUIT_NAME_INDEX],
                    Integer.parseInt(parts[FRUIT_QUANTITY_INDEX])));
        }
        return transactions;
    }
}
