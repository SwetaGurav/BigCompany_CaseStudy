package test;

import main.java.CompanyAnalysis;
import main.java.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyAnalysisTest {

    private List<Employee> employees;
    private Map<String, Employee> employeeMap;


    @BeforeEach
    public void setup() {
        employees = Arrays.asList(
                new Employee("123","Joe","Doe",60000,null),
                new Employee("124","Martin","Chekov",45000,"123"),
                new Employee("125","Bob","Ronstad",47000,"123"),
                new Employee("300","Alice","Hasacat",50000,"124"),
                new Employee("305","Brett","Hardleaf",34000,"300")
        );
        employeeMap = CompanyAnalysis.createEmployeeMap(employees);
    }

    @Test
    void testCheckSalaryIssues() {
        List<String> issues = CompanyAnalysis.checkSalaryIssues(employees, employeeMap);
        assertFalse(issues.isEmpty());
    }


    @Test
    void testCheckReportingLinesIssues() {
        List<String> reportLines = CompanyAnalysis.checkReportingLinesIssues(employees, employeeMap);
        assertTrue(reportLines.isEmpty());
    }

}
