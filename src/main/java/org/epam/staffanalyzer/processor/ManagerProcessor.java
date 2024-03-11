package org.epam.staffanalyzer.processor;

import org.epam.staffanalyzer.entity.Employee;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerProcessor {
    private static final double MIN_SALARY_PERCENTAGE = 0.2;
    private static final double MAX_SALARY_PERCENTAGE = 0.5;
    private static final int MAX_REPORTING_LINES = 4;
    private final Map<Integer, Employee> employees;
    private final Map<Employee, List<Employee>> managerMap = new LinkedHashMap<>();

    private final List<String> underPaidManagers = new ArrayList<>();
    private final List<String> overPaidManagers = new ArrayList<>();
    private List<String> tooLongReportingLineEmployees = new ArrayList<>();

    private int ceoId;

    public ManagerProcessor(Map<Integer, Employee> employees) {
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
        for (Employee employee : employees.values()) {
            int managerId = employee.getManagerId();
            if (managerId == -1) {
                ceoId = employee.getId();
            } else {
                Employee manager = employees.get(managerId);
                managerMap.computeIfAbsent(manager, k -> new ArrayList<>()).add(employee);

                if (managerId != ceoId) {
                    employee.setManagerLineLength(manager.getManagerLineLength() + 1);
                }
            }
        }
    }

    public void analyzeManagerSalary() {
        for (Map.Entry<Employee, List<Employee>> entry : managerMap.entrySet()) {
            Employee manager = entry.getKey();
            int managerId = manager.getId();
            List<Employee> subordinates = entry.getValue();

            double averageSalary = calculateAverageDirectSubordinateSalary(subordinates);
            double managerSalary = manager.getSalary();

            if (managerSalary - averageSalary < MIN_SALARY_PERCENTAGE * averageSalary) {
                underPaidManagers.add(String.format("Manager %d earns less than they should by %.1f", managerId,
                        ((MIN_SALARY_PERCENTAGE * averageSalary) - (managerSalary - averageSalary))));
            } else if (managerSalary - averageSalary > MAX_SALARY_PERCENTAGE * averageSalary) {
                overPaidManagers.add(String.format("Manager %d earns more than they should by %.1f", managerId,
                        ((managerSalary - averageSalary) - (MAX_SALARY_PERCENTAGE * averageSalary))));
            }
        }

        tooLongReportingLineEmployees = employees.values().stream()
                .filter(employee -> employee.getManagerLineLength() > MAX_REPORTING_LINES)
                .map(employee -> String.format("Employee %d has a reporting line that is too long.", employee.getId()))
                .collect(Collectors.toList());
    }

    private static double calculateAverageDirectSubordinateSalary(List<Employee> employees) {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }
}
