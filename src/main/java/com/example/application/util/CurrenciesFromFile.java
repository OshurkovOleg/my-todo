package com.example.application.util;

import com.example.application.Settings;
import com.example.application.exceptions.ConverterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrenciesFromFile {

    public static final String UNABLE_TO_PARSE_FILE = "Unable to parse file";

    public static List<String> getAllCurrenciesFromFile() {

        try (Stream<String> lines = Files.lines(Path.of(Settings.PATH_TO_FILE_WITH_ALL_CURRENCY_TYPE))) {

            return lines.map(line -> line.split("\""))
                    .flatMap(Arrays::stream)
                    .filter(line -> line.length() == 3)
                    .collect(Collectors.toList());

        } catch (InvalidPathException | IOException e) {
            throw new ConverterException(UNABLE_TO_PARSE_FILE + e);
        }
    }
}
