package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class CsvWriterServiceImplTest {
    private static final String TEST_DATA = "fruit,quantity\nbanana,10\napple,5";
    private final WriterService writerService = new CsvWriterServiceImpl();
    private File tempFile;

    @Test
    void writeToFile_validData_successfullyWritesToFile() throws IOException {
        tempFile = File.createTempFile("test", ".csv");
        writerService.writeToFile(tempFile.getAbsolutePath(), TEST_DATA);

        List<String> lines = Files.readAllLines(tempFile.toPath());

        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("banana,10", lines.get(1));
        assertEquals("apple,5", lines.get(2));
    }

    @Test
    void writeToFile_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid-path/test.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(invalidPath, TEST_DATA)
        );

        assertTrue(exception.getMessage().contains("Failed to write to file"));
    }

    @AfterEach
    void cleanUp() {
        if (tempFile != null) {
            tempFile.deleteOnExit();
        }
    }
}
