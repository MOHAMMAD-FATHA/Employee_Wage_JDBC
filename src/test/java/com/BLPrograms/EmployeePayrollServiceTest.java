package com.BLPrograms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollServiceTest {
    EmployeePayrollService employeePayrollService = null;

    @Before
    public void setUp() throws Exception {
        employeePayrollService = new EmployeePayrollService();
    }

    /**
     * Purpose : To test whether the number of records present in the database matches with the expected value
     */
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assert.assertEquals(2, employeePayrollData.size());
    }

    /**
     * Purpose : To test whether the salary is updated in the database and is synced with the DB
     *           - Read the values from the database
     *           - Update the salary in the database
     *           - Test whether the database is correctly synced or not
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Fatha", 1800000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Fatha");
        Assert.assertTrue(result);
    }

    /**
     * Purpose : To test whether the salary is updated in the database and is synced with the DB using JDBC PreparedStatement
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDBUsingPreparedStatement() throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalaryUsingPreparedStatement("Mohammad", 5000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mohammad");
        Assert.assertTrue(result);
    }

    /**
     * Purpose : To test whether the count of the retrieved data who have joined in a particular data range matches with the expected value
     */
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchTheEmployeeCount() throws EmployeePayrollException {
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2017, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(EmployeePayrollService.IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(2, employeePayrollData.size());
    }
}
