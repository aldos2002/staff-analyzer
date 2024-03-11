package org.epam.staffanalyzer;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.exception.CSVFormatException;
import org.epam.staffanalyzer.parser.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class StaffAnalyzer {
    static final Logger logger = LoggerFactory.getLogger(StaffAnalyzer.class);

    public static void main(String[] args) {
        CSVParser parser = new CSVParser();
        try {
            List<Employee> employees = parser.readEmployeesFromFile("employees.csv");
            logger.info("Number of employees = {}", employees.size());
        } catch (IOException | CSVFormatException exception) {
            logger.error("CSV parsing failed:", exception);
        }
    }
}