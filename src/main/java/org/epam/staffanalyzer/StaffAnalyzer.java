package org.epam.staffanalyzer;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.parser.CSVParser;

import java.util.List;

public class StaffAnalyzer {
    public static void main(String[] args) {
        CSVParser parser = new CSVParser();
        List<Employee> employees = parser.readEmployeesFromFile("employees.csv");
        System.out.println(employees.size());
    }


}