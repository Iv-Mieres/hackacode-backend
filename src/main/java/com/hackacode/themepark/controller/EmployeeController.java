package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.service.IEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<HttpStatus> saveEmployee(@Valid @RequestBody EmployeeDTOReq employeeDTO) throws Exception {
         employeeService.saveEmployee(employeeDTO);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeDTORes> getEmployee(@PathVariable Long employee_id) throws Exception {
       return ResponseEntity.ok(employeeService.getEmployeeById(employee_id));
    }

    @GetMapping()
    public ResponseEntity<Page<EmployeeDTORes>> getAllEmployees(Pageable pageable){
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateEmployee(@Valid @RequestBody EmployeeDTOReq employeeDTORes) throws Exception {
        employeeService.updateEmployee(employeeDTORes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long employeeId) throws Exception {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
