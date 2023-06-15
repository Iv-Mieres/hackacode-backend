package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {

    void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception;
    EmployeeDTORes getEmployeeById(Long employeeId) throws Exception;
    Page<EmployeeDTORes> getAllEmployees(Pageable pageable);
    void updateEmployee(EmployeeDTOReq employeeDTO) throws Exception;
    void deleteEmployee(Long employeeID) throws Exception;
}
