package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTOres;
import com.hackacode.themepark.service.IEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleado")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public void saveEmployee(@Valid @RequestBody EmployeeDTOReq employeeDTO) throws Exception {
         employeeService.saveEmployee(employeeDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/ver/{employee_id}")
    public ResponseEntity<EmployeeDTOres> getEmployee(@PathVariable Long employee_id){
       return ResponseEntity.ok(employeeService.getEmployeeById(employee_id));
    }


}
