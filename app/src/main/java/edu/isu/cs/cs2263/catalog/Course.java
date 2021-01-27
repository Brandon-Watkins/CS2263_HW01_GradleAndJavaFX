/**
 * Course class, utilizing a static array of departments, as per my assignment instructions.
 *
 * @author Brandon Watkins
 * @version 1.0.0
 * @since 2021-01-20
 */
package edu.isu.cs.cs2263.catalog;

public class Course implements Comparable<Course> {
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

    /**
     *
     * @param courseNumber String representation of the course's "number" (ie. CS *2263*).
     * @param courseName The course's name (ie. *Advanced Object-Oriented Programming*).
     * @param courseCredits The number of credits the course is worth (ie. *3*).
     * @param departmentIndex The index of the courses department in the departments array.
     */
    public Course(String courseNumber, String courseName, int courseCredits, int departmentIndex) {
        number = courseNumber;
        name = courseName;
        credits = courseCredits;
        department = DEPARTMENTS[departmentIndex];
    }

    /**
     * The string representation of the object.
     * @return String (CS  2263  Advanced Object-Oriented Programming  (3 cr))
     */
    public String toString() { return getDepartment().getCode() + "  " + number + "  " + name + "  (" + credits + " cr)"; }

    @Override
    public int compareTo(Course c) {
        return (getDepartment().getCode() + " " + number).compareTo(c.getDepartment().getCode() + " " + c.getNumber());
    }
}
