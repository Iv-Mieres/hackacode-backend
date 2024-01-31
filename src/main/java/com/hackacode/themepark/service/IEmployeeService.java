package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.exception.DniNotFoundException;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {

    void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception;
    EmployeeDTORes getEmployeeById(Long employeeId) throws IdNotFoundException;

    EmployeeDTORes getEmployeeByDni(String employeeDni) throws DniNotFoundException;

    Page<EmployeeDTORes> getAllEmployees(Pageable pageable);
    void updateEmployee(EmployeeDTOReq employeeDTO) throws Exception;
    void deleteEmployee(Long employeeID) throws IdNotFoundException;

}
