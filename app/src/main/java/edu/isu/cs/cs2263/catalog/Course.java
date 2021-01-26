package edu.isu.cs.cs2263.catalog;

import java.util.List;

public class Course {
    private static final Department[] DEPARTMENTS = {
            new Department("Computer Science", "CS"),
            new Department("Mathematics", "MATH"),
            new Department("Chemistry", "CHEM"),
            new Department("Physics", "PHYS"),
            new Department("Botany", "BTNY"),
            new Department("Zoology", "ZOO")
    };

    private String number;
    private String name;
    private int credits;
    private Department department;

    public String getNumber() { return number; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public Department getDepartment() { return department; }
    public static Department[] getDepartments() { return DEPARTMENTS; }

    public Course(String courseNumber, String courseName, int courseCredits, int departmentIndex) {
        number = courseNumber;
        name = courseName;
        credits = courseCredits;
        department = DEPARTMENTS[departmentIndex];
    }

    public String toString() { return number + " " + name; }
}
