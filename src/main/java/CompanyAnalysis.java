package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyAnalysis {

    public static List<Employee> readEmployeeDetails(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            String id = values[0];
            String firstName = values[1];
            String lastName = values[2];
            double salary = Double.parseDouble(values[3]);
            String managerId = values[4].isEmpty() ? null : values[4];
            employees.add(new Employee(id, firstName, lastName, salary, managerId));
        }
        reader.close();
        return employees;
    }


    public static Map<String, Employee> createEmployeeMap(List<Employee> employees) {
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
        }
        return employeeMap;
    }

    public static List<String> checkSalaryIssues(List<Employee> employees, Map<String, Employee> employeeMap) {
        List<String> salaryIssueList = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.managerId != null) {
                List<Employee> subordinates = getSubordinates(emp, employees);
                if (subordinates.isEmpty()) {
                    continue;
                }

                double avgSalary = subordinates.stream()
                        .mapToDouble(sb -> sb.salary)
                        .average()
                        .orElse(0.0);

                double lowerLimit = avgSalary * 0.2;
                double upperLimit = avgSalary * 0.5;
                if (emp.salary < lowerLimit) {
                    salaryIssueList.add(emp.id);
                    System.out.println("Employee id: " + emp.id +  " earns " + (lowerLimit - emp.salary) + " less than they should.");
                } else if (emp.salary > upperLimit) {
                    salaryIssueList.add(emp.id);
                    System.out.println("Employee id: " + emp.id + " earns " + (emp.salary - upperLimit) + " more than they should.");
                }
            }
        }
        return salaryIssueList;
    }

    private static List<Employee> getSubordinates(Employee manager, List<Employee> employees) {
        List<Employee> subordinates = new ArrayList<>();
        for (Employee emp : employees) {
            if (manager.id.equals(emp.managerId)) {
                subordinates.add(emp);
            }
        }
        return subordinates;
    }


    public static List<String> checkReportingLinesIssues(List<Employee> employees, Map<String, Employee> employeeMap) {
        List<String> reportingIssuesList = new ArrayList<>();
        for (Employee employee : employees) {
            int reportLines = getReportingLines(employee, employeeMap);
            if (reportLines > 4) {
                reportingIssuesList.add(employee.id);
                System.out.println("Employee id: " + employee.id + " has " + reportLines + " managers between them and the CEO.");
            }
        }
        return reportingIssuesList;
    }

    private static int getReportingLines(Employee employee, Map<String, Employee> employeeMap) {
        int reportingLines = 0;
        Employee currentEmployee = employee;
        while (currentEmployee.managerId != null) {
            currentEmployee = employeeMap.get(currentEmployee.managerId);
            reportingLines++;
        }
        return reportingLines;
    }


}
