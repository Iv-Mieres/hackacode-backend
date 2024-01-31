package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.exception.DniNotFoundException;
import com.hackacode.themepark.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Empleados")
@RestController
@RequestMapping("/api/empleados")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Operation(
            summary = "Guarda un empleado",
            description = "Guarda el empleado en BD y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping
    public ResponseEntity<HttpStatus> saveEmployee(@Valid @RequestBody EmployeeDTOReq employeeDTO) throws Exception {
         employeeService.saveEmployee(employeeDTO);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un empleado",
            description = "Busca un empleado por id y devuelve un Codigo de estado 200 y los datos del empleado"
    )
    @GetMapping("/{employee_id}")
    public ResponseEntity<EmployeeDTORes> getEmployee(@PathVariable Long employee_id) throws Exception {
       return ResponseEntity.ok(employeeService.getEmployeeById(employee_id));
    }

    @Operation(
            summary = "Trae un empleado por dni",
            description = "Busca un empleado por dni y devuelve un Codigo de estado 200 y los datos del empleado"
    )
    @GetMapping("/dni/{employeeDni}")
    public ResponseEntity<EmployeeDTORes> getEmployeeByDni(@PathVariable String employeeDni) throws DniNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployeeByDni(employeeDni));
    }

    @Operation(
            summary = "Traer todos los empleados",
            description = "Trae todos los empleados de base de datos y devuelve un Codigo de estado 200 y el listado de empleados"
    )
    @GetMapping()
    public ResponseEntity<Page<EmployeeDTORes>> getAllEmployees(Pageable pageable){
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateEmployee(@Valid @RequestBody EmployeeDTOReq employeeDTORes) throws Exception {
        employeeService.updateEmployee(employeeDTORes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Actualiza un empleado",
            description = "Busca un empleado por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long employeeId) throws Exception {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
