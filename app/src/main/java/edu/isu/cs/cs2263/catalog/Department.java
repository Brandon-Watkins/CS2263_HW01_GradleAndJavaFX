package edu.isu.cs.cs2263.catalog;

public class Department {
    private String name;
    private String code;

    public String getName() { return name; }
    public String getCode() { return code; }

    public Department(String departmentName, String departmentCode) { name = departmentName; code = departmentCode; }

    public String toString() { return name; }
}
