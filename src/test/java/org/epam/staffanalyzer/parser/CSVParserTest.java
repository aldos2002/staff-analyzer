package org.epam.staffanalyzer.parser;

import org.epam.staffanalyzer.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVParserTest {

    @Test
    void readEmployeesFromFile() {
        CSVParser parser = new CSVParser();

        List<Employee> employees = parser.readEmployeesFromFile("employees.csv");

        assertEquals(5, employees.size());
    }
}