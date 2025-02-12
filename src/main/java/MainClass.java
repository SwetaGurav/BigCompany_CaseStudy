package main.java;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static main.java.CompanyAnalysis.*;

public class MainClass {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\employees.csv";
        List<Employee> employees = readEmployeeDetails(filePath);

        Map<String, Employee> employeeMap = createEmployeeMap(employees);

        checkSalaryIssues(employees, employeeMap);

        checkReportingLinesIssues(employees, employeeMap);
    }
}
