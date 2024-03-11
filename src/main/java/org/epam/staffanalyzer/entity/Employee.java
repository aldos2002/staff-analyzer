package org.epam.staffanalyzer.entity;

public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final int managerId;
    private int managerLineLength;

    public Employee(int id, String firstName, String lastName, double salary, int managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getSalary() {
        return salary;
    }

    public int getManagerId() {
        return managerId;
    }

    public int getManagerLineLength() {
        return managerLineLength;
    }

    public void setManagerLineLength(int managerLineLength) {
        this.managerLineLength = managerLineLength;
    }
}
