package org.epam.staffanalyzer.parser;

import org.epam.staffanalyzer.entity.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public List<Employee> readEmployeesFromFile(String filename) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                double salary = parts[3].isEmpty() ? 0 : Double.parseDouble(parts[3]);
                int managerId = parts.length > 4 && !parts[4].isEmpty() ? Integer.parseInt(parts[4]) : -1;
                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
