package org.epam.staffanalyzer.processor;

import org.epam.staffanalyzer.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerProcessorTest {
    @Test
    void analyzeManagerSalary() {

        List<Employee> employees = new ArrayList<>();
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(0, managerProcessor.getUnderPaidManagers().size());
        assertEquals(0, managerProcessor.getOverPaidManagers().size());
    }

    @Test
    void analyzeManagerSalary_returnsCorrectUnderPaidManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 60000, -1);
        Employee employee1 = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 124);

        List<Employee> employees = new ArrayList<>();
        employees.add(ceo);
        employees.add(employee1);
        employees.add(employee3);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getUnderPaidManagers().size());
        assertEquals("Manager 124 earns less than they should by 20280.0", managerProcessor.getUnderPaidManagers().get(0));
        assertEquals(0, managerProcessor.getOverPaidManagers().size());
    }

    @Test
    void analyzeManagerSalary_returnsCorrectOverPaidManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 66000, -1);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 123);
        Employee employee4 = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        List<Employee> employees = new ArrayList<>();
        employees.add(ceo);
        employees.add(employee3);
        employees.add(employee4);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

        assertEquals(1, managerProcessor.getOverPaidManagers().size());
        assertEquals("Manager 300 earns more than they should by 3400.0", managerProcessor.getOverPaidManagers().get(0));
        assertEquals(0, managerProcessor.getUnderPaidManagers().size());
    }

    @Test
    void analyzeManagerSalary_returnsCorrectEmployeesWithTooManyManagers() {
        Employee ceo = new Employee(123, "Joe", "Doe", 66000, -1);
        Employee employee3 = new Employee(300, "Alice", "Hasacat", 54400, 123);
        Employee employee4 = new Employee(305, "Brett", "Hardleaf", 34000, 300);
        Employee employee5 = new Employee(306, "Brett", "Hardleaf", 34000, 305);
        Employee employee6 = new Employee(307, "Brett", "Hardleaf", 34000, 306);
        Employee employee7 = new Employee(308, "Brett", "Hardleaf", 34000, 307);

        List<Employee> employees = new ArrayList<>();
        employees.add(ceo);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);
        employees.add(employee7);
        ManagerProcessor managerProcessor = new ManagerProcessor(employees);

        managerProcessor.analyzeManagerSalary();

//        assertEquals(0, managerProcessor.getOverPaidManagers().size());
//        assertEquals(3, managerProcessor.getUnderPaidManagers().size());
        assertEquals(1, managerProcessor.getTooLongReportingLineEmployees().size());
        assertEquals("Employee 308 has a reporting line that is too long.", managerProcessor.getTooLongReportingLineEmployees().get(0));
    }
}