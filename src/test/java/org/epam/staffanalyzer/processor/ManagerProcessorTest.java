package org.epam.staffanalyzer.processor;

import org.epam.staffanalyzer.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerProcessorTest {
    @Test
    void analyzeManagerSalary() {
        Map<Integer, Employee> employees = new HashMap<>();
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(0, managerProcessor.getUnderPaidManagers().size());
        assertEquals(0, managerProcessor.getOverPaidManagers().size());
    }

    @Test
    void analyzeManagerSalary_calculateCorrectAverageDirectSubordinateSalary() {
        int ceoSalary = 55000;
        int employee1Salary = 45000;
        int employee2Salary = 47000;
        Employee ceo = new Employee(123, "Joe", "Doe", ceoSalary, -1);
        Employee employee1 = new Employee(124, "Martin", "Chekov", employee1Salary, 123);
        Employee employee2 = new Employee(300, "Alice", "Hasacat", employee2Salary, 123);
        double minimumManagerSalary = ((employee1Salary + employee2Salary)) / 2.0 * 1.2;
        double expectedUderpaidAmount = minimumManagerSalary - ceoSalary;

        Map<Integer, Employee> employees = new HashMap<>();
        employees.put(ceo.getId(), ceo);
        employees.put(employee1.getId(), employee1);
        employees.put(employee2.getId(), employee2);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getUnderPaidManagers().size());
        assertEquals(String.format("Manager 123 earns less than they should by %.1f", expectedUderpaidAmount),
                managerProcessor.getUnderPaidManagers().get(0));
    }

    @Test
    void analyzeManagerSalary_returnsCorrectUnderPaidManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 60000, -1);
        Employee employee1 = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 124);

        Map<Integer, Employee> employees = new HashMap<>();
        employees.put(ceo.getId(), ceo);
        employees.put(employee1.getId(), employee1);
        employees.put(employee3.getId(), employee3);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getUnderPaidManagers().size());
        assertEquals("Manager 124 earns less than they should by 20280.0",
                managerProcessor.getUnderPaidManagers().get(0));
    }

    @Test
    void analyzeManagerSalary_returnsCorrectOverPaidManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 66000, -1);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 123);
        Employee employee4 = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        Map<Integer, Employee> employees = new HashMap<>();
        employees.put(ceo.getId(), ceo);
        employees.put(employee3.getId(), employee3);
        employees.put(employee4.getId(), employee4);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getOverPaidManagers().size());
        assertEquals("Manager 300 earns more than they should by 3400.0",
                managerProcessor.getOverPaidManagers().get(0));
    }

    @Test
    void analyzeManagerSalary_returnsCorrectEmployeesWithTooManyManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 66000, -1);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 123);
        Employee employee4 = new Employee(305, "Brett", "Hardleaf", 34000, 300);
        Employee employee5 = new Employee(306, "Sam", "Bigot", 34000, 305);
        Employee employee6 = new Employee(307, "Tom", "Soyer", 34000, 306);
        Employee employee7 = new Employee(308, "Chris", "Evans", 34000, 307);
        Employee employee8 = new Employee(309, "Jane", "Bishop", 34000, 308);

        Map<Integer, Employee> employees = new LinkedHashMap<>();
        employees.put(ceo.getId(), ceo);
        employees.put(employee3.getId(), employee3);
        employees.put(employee4.getId(), employee4);
        employees.put(employee5.getId(), employee5);
        employees.put(employee6.getId(), employee6);
        employees.put(employee7.getId(), employee7);
        employees.put(employee8.getId(), employee8);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getTooLongReportingLineEmployees().size());
        assertEquals("Employee 309 has a reporting line that is too long.",
                managerProcessor.getTooLongReportingLineEmployees().get(0));
    }
}