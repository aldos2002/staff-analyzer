package org.epam.staffanalyzer;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.exception.CSVFormatException;
import org.epam.staffanalyzer.parser.CSVParser;
import org.epam.staffanalyzer.processor.ManagerProcessor;

import java.io.IOException;
import java.util.List;

public class StaffAnalyzer {

    public static void main(String[] args) {
        CSVParser parser = new CSVParser();

        try {
            List<Employee> employees = parser.readEmployeesFromFile("employees.csv");

            ManagerProcessor managerProcessor = new ManagerProcessor(employees);
            managerProcessor.analyzeManagerSalary();

            printResult(managerProcessor);
        } catch (IOException | CSVFormatException exception) {
            exception.printStackTrace();
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

        System.out.println();
        System.out.println("-----EMPLOYEES WITH TOO MANY MANAGERS-----");
        for (String employee : managerProcessor.getTooLongReportingLineEmployees()) {
            System.out.println(employee);
        }
    }
}