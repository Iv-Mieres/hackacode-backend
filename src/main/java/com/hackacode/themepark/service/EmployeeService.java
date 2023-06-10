package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTOres;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.IEmployeeUserRepository;
import com.hackacode.themepark.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService implements IEmployeeService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmployeeUserRepository employeeUserRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public EmployeeService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Guarda el Empleado con su respectivo Rol
    @Override
    public void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setEnable(true);

        Role roleBD = roleRepository.findById(employeeDTO.getRoleId())
                .orElseThrow(() -> new Exception("El id " + employeeDTO.getRoleId() + " no existe"));
        employee.setRoles(Set.of(roleBD));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeUserRepository.save(employee);
    }

    @Override
    public EmployeeDTOres getEmployeeById(Long employeeId) {
       var employeeBD = employeeUserRepository.findById(employeeId).orElse(null);
       return modelMapper.map(employeeBD, EmployeeDTOres.class);
    }

    @Override
    public List<EmployeeDTOres> getAllEmployees() {
        var employees = employeeUserRepository.findAll();
        var employeesDTO = new ArrayList<EmployeeDTOres>();

        for (Employee employee: employees) {
           employeesDTO.add(modelMapper.map(employee, EmployeeDTOres.class));
        }
        return employeesDTO;
    }

    @Override
    public void updateEmployee(EmployeeDTOReq employeeDTO) {

    }

    @Override
    public void deleteEmployee(Long employeeID) {

    }
}
