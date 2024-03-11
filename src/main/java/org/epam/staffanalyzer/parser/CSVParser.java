package org.epam.staffanalyzer.parser;

import org.epam.staffanalyzer.entity.Employee;
import org.epam.staffanalyzer.exception.CSVFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CSVParser {
    public Map<Integer, Employee> readEmployeesFromFile(String filename) throws CSVFormatException, IOException {
        List<String> lines = readLines(filename);
        return parseEmployees(lines);
    }

    private List<String> readLines(String filename) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/" + filename))))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }

    public Map<Integer, Employee> parseEmployees(List<String> lines) throws CSVFormatException {
        if (lines.size() > 1000) {
            throw new CSVFormatException("Number of rows in CSV file should be up to 1000");
        }

        Map<Integer, Employee> employees = new LinkedHashMap<>();
        lines.remove(0);
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String firstName = parts[1];
            String lastName = parts[2];
            double salary = parts[3].isEmpty() ? 0 : Double.parseDouble(parts[3]);
            int managerId = parts.length > 4 && !parts[4].isEmpty() ? Integer.parseInt(parts[4]) : -1;

            employees.put(id, new Employee(id, firstName, lastName, salary, managerId));
        }
        return employees;
    }
}
