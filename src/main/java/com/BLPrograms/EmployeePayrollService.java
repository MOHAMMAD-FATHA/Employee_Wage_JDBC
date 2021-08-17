package com.BLPrograms;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollService {
    private final EmployeePayrollDBService employeePayrollDBService;
    private List<EmployeePayrollData> employeePayrollList;

    public enum IOService {
        DB_IO
    }

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    /**
     * Purpose : To get the list of employee payroll from the database
     */
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws EmployeePayrollException {
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollList = employeePayrollDBService.readData();
        return this.employeePayrollList;
    }

    /**
     * Purpose : To update the Employee Salary in the database
     *           If the value is updated, the result value is greater than 0; else 0
     *           Match the given name with the EmployeePayrollData list
     *           If found, assign the given salary to the EmployeePayrollData list
     */

    public void updateEmployeeSalary(String name, double salary) throws EmployeePayrollException {
        int result = employeePayrollDBService.updateEmployeeData(name, salary);
        if(result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if( employeePayrollData != null )
            employeePayrollData.salary = salary;
    }

    /**
     * Purpose : To update the Employee Salary in the database using Prepared statement
     *           If the value is updated, the result value is greater than 0; else 0
     *           Match the given name with the EmployeePayrollData list
     *           If found, assign the given salary to the EmployeePayrollData list
     */

    public void updateEmployeeSalaryUsingPreparedStatement(String name, double salary) throws EmployeePayrollException {
        int result = employeePayrollDBService.updateEmployeeDataPreparedStatement(name, salary);
        if(result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if( employeePayrollData != null )
            employeePayrollData.salary = salary;
    }

    /**
     * Purpose : To check whether the EmployeePayrollData is in sync with the DB
     *           Use to equals() to compare the values
     */

    public boolean checkEmployeePayrollInSyncWithDB(String name) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    /**
     * Purpose : Check the EmployeePayrollData list for the name
     *           If found, return the value else return null
     */

    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Purpose : Retrieve the data for a particular date range
     */
    public List<EmployeePayrollData> readEmployeePayrollForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) throws EmployeePayrollException {
        if( ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeeForDateRange(startDate, endDate);
        return null;
    }
}