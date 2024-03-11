package org.epam.staffanalyzer.processor;

import org.epam.staffanalyzer.entity.Employee;

import java.util.*;

public class ManagerProcessor {
    private static final double MIN_SALARY_PERCENTAGE = 0.2;
    private static final double MAX_SALARY_PERCENTAGE = 0.5;
    private static final int MAX_REPORTING_LINES = 4;
    List<Employee> employees;
    Map<Integer, List<Employee>> managerMap = new HashMap<>();

    List<String> underPaidManagers = new ArrayList<>();
    List<String> overPaidManagers = new ArrayList<>();
    List<String> tooLongReportingLineEmployees = new ArrayList<>();

    public ManagerProcessor(List<Employee> employees) {
        this.employees = employees;
        initManagerMap();
    }

    public List<String> getUnderPaidManagers() {
        return underPaidManagers;
    }

    public List<String> getOverPaidManagers() {
        return overPaidManagers;
    }

    public List<String> getTooLongReportingLineEmployees() {
        return tooLongReportingLineEmployees;
    }

    private void initManagerMap() {
        for (Employee employee : employees) {
            int managerId = employee.getManagerId();
            if (managerId != -1) {
                managerMap.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
            }
        }
    }

    public void analyzeManagerSalary() {
        for (Map.Entry<Integer, List<Employee>> entry : managerMap.entrySet()) {
            int managerId = entry.getKey();
            List<Employee> subordinates = entry.getValue();

            double averageSalary = calculateAverageSubordinateSalary(subordinates);
            double managerSalary = findManagerSalary(employees, managerId);

            if (managerSalary - averageSalary < MIN_SALARY_PERCENTAGE * averageSalary) {
                underPaidManagers.add(String.format("Manager %d earns less than they should by %.1f", managerId,
                        ((MIN_SALARY_PERCENTAGE * averageSalary) - (managerSalary - averageSalary))));
            } else if (managerSalary - averageSalary > MAX_SALARY_PERCENTAGE * averageSalary) {
                overPaidManagers.add(String.format("Manager %d earns more than they should by %.1f", managerId,
                        ((managerSalary - averageSalary) - (MAX_SALARY_PERCENTAGE * averageSalary))));
            }
        }

        Set<Integer> tooLongReportingLines = findEmployeesWithLongReportingLines(employees);
        for (int employeeId : tooLongReportingLines) {
            tooLongReportingLineEmployees.add(String.format("Employee %d has a reporting line that is too long.",
                    employeeId));
        }
    }

    private static double calculateAverageSubordinateSalary(List<Employee> employees) {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }
        return totalSalary / employees.size();
    }

    private static double findManagerSalary(List<Employee> employees, int managerId) {
        for (Employee employee : employees) {
            if (employee.getId() == managerId) {
                return employee.getSalary();
            }
        }
        return 0;
    }

    private static Set<Integer> findEmployeesWithLongReportingLines(List<Employee> employees) {
        Set<Integer> tooLongReportingLines = new HashSet<>();
        Map<Integer, Integer> reportingLineLengths = new HashMap<>();
        for (Employee employee : employees) {
            int length = 0;
            int managerId = employee.getManagerId();
            while (managerId != -1) {
                length++;
                managerId = findManagerById(employees, managerId).getManagerId();
            }
            reportingLineLengths.put(employee.getId(), length);
        }
        for (Map.Entry<Integer, Integer> entry : reportingLineLengths.entrySet()) {
            if (entry.getValue() > MAX_REPORTING_LINES) {
                tooLongReportingLines.add(entry.getKey());
            }
        }
        return tooLongReportingLines;
    }

    private static Employee findManagerById(List<Employee> employees, int managerId) {
        for (Employee employee : employees) {
            if (employee.getId() == managerId) {
                return employee;
            }
        }
        return null;
    }
}
