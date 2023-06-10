package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTOres;

import java.util.List;

public interface IEmployeeService {

    void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception;
    EmployeeDTOres getEmployeeById(Long employeeId);
    List<EmployeeDTOres> getAllEmployees();
    void updateEmployee(EmployeeDTOReq employeeDTO);
    void deleteEmployee(Long employeeID);
}
