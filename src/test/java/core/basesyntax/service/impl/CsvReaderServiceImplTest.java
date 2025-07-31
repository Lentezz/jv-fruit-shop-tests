package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderServiceImplTest {
    private static List<String> fruitsInfo;
    private static File FILE;
    private String filePath;
    private ReaderService readerService;

    @BeforeEach
    public void setUp() {
        readerService = new CsvReaderServiceImpl();
        fruitsInfo = new ArrayList<>();
        try {
            FILE = File.createTempFile("test", ".csv");
            filePath = FILE.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readerServiceShouldCorrectReadFromFile() {
        generateCsvFile();
        List<String> list = readerService.readFromFile(filePath);
        System.out.println(list);
        Assertions.assertEquals(fruitsInfo, list);
    }

    @Test
    public void readerServiceShouldCorrectReadFromEmptyFile() {
        Assertions.assertEquals(fruitsInfo, readerService.readFromFile(filePath));
    }

    @Test
    public void readerServiceShouldThrowExceptionWhenFileNotExist() {
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readFromFile("FILE_PATH"));
        Assertions.assertEquals(exception.getMessage(), "Failed to read file: " + "FILE_PATH");
    }

    @AfterEach
    public void tearDown() {
        FILE.deleteOnExit();
    }

    private static void generateCsvFile() {
        addTitleToList(fruitsInfo);
        addRandomFruitsToList(fruitsInfo);
        try (PrintWriter pw = new PrintWriter(FILE)) {
            fruitsInfo
                    .forEach(pw::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addTitleToList(List<String> fruitsInfo) {
        fruitsInfo.add("type,fruit,quantity");
    }

    private static void addRandomFruitsToList(List<String> fruitsInfo) {
        int maxFruitsCount = 15;
        Random random = new Random();
        String[] possibleFruits = new String[]{"apple", "banana", "peach", "mandarin", "orange"};
        String[] possibleOperations = new String[]{"b", "s", "p", "r"};
        int fruitsInfoSize = new Random().nextInt(maxFruitsCount);
        for (int i = 0; i < fruitsInfoSize; i++) {
            String fruit = possibleOperations[random.nextInt(possibleOperations.length)] + ","
                    + possibleFruits[random.nextInt(possibleFruits.length)] + ","
                    + random.nextInt(20);
            fruitsInfo.add(fruit);
        }
    }
}
