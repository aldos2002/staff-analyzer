package org.epam.staffanalyzer;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.exception.CSVFormatException;
import org.epam.staffanalyzer.parser.CSVParser;
import org.epam.staffanalyzer.processor.ManagerProcessor;
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
            logger.debug("Number of employees = {}", employees.size());

            ManagerProcessor managerProcessor = new ManagerProcessor(employees);
            managerProcessor.analyzeManagerSalary();

            printResult(managerProcessor);
        } catch (IOException | CSVFormatException exception) {
            logger.error("CSV parsing failed:", exception);
        }
    }

    private static void printResult(ManagerProcessor managerProcessor) {
        System.out.println("------------UNDERPAID MANAGERS------------");
        for (String underPaidManager : managerProcessor.getUnderPaidManagers()) {
            System.out.println(underPaidManager);
        }

        System.out.println();
        System.out.println("------------OVERPAID MANAGERS------------");
        for (String overPaidManger : managerProcessor.getOverPaidManagers()) {
            System.out.println(overPaidManger);
        }
    }
}