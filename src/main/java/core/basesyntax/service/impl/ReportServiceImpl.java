package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String COMMA_SEPARATOR = ",";

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder("fruit,quantity").append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : Storage.getAll().entrySet()) {
            sb.append(entry.getKey())
                    .append(COMMA_SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}
