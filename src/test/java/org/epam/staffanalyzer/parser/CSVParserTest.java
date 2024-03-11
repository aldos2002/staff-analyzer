package org.epam.staffanalyzer.parser;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.exception.CSVFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVParserTest {

    @Test
    void readEmployeesFromFile() throws Exception {
        CSVParser parser = new CSVParser();

        Map<Integer, Employee> employees = parser.readEmployeesFromFile("employees.csv");

        assertEquals(9, employees.size());
    }

    @Test
    void readEmployeesFromFile_throwsException_rowsMoreThan1000() throws Exception {
        CSVParser parser = new CSVParser();
        List<String> lines = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            lines.add("");
        }

        assertThrows(CSVFormatException.class, () -> parser.parseEmployees(lines));
    }
}