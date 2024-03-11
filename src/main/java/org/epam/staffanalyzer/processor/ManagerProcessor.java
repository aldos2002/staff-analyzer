package org.epam.staffanalyzer.processor;

import org.epam.staffanalyzer.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerProcessor {
    private static final double MIN_SALARY_PERCENTAGE = 0.2;
    private static final double MAX_SALARY_PERCENTAGE = 0.5;

    List<Employee> employees;
    Map<Integer, List<Employee>> managerMap = new HashMap<>();

    List<String> underPaidManagers = new ArrayList<>();
    List<String> overPaidManagers = new ArrayList<>();

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
}
