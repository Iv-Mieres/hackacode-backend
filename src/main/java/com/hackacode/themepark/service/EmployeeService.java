package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.IEmployeeUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmployeeUserRepository employeeUserRepository;

    //CREA UN EMPLEADO CON SU ROL
    @Override
    public void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception {
        //valida que el dni y username sean únicos
        this.validateEmployeeDataBeforeSaving(employeeDTO.getDni(), employeeDTO.getUsername());

        //valida que el juego no sea nulo y que su id no exista en la BD
        if (!Objects.isNull(employeeDTO.getGame()) &&
                !employeeUserRepository.existsByGame_id(employeeDTO.getGame().getId())) {
            throw new Exception("El juego que intenta ingresar no existe. Ingrese un juego existente");
        }
        else {
            //convierte el DTO a Entity y lo guarda
            var employee = modelMapper.map(employeeDTO, Employee.class);
            employee.setEnable(true);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeUserRepository.save(employee);
        }
    }

    //MUESTRA EMPLEADO POR ID
    @Override
    public EmployeeDTORes getEmployeeById(Long employeeId) throws Exception {
        var employeeBD = employeeUserRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("El id " + employeeId + " no existe"));
        return modelMapper.map(employeeBD, EmployeeDTORes.class);
    }

    //LISTA DTO DE EMPLEADOS PAGINADOS
    @Override
    public Page<EmployeeDTORes> getAllEmployees(Pageable pageable) {
        var employees = employeeUserRepository.findAllByIsEnable(true, pageable);
        var employeesDTO = new ArrayList<EmployeeDTORes>();

        for (Employee employee : employees) {
            employeesDTO.add(modelMapper.map(employee, EmployeeDTORes.class));
        }
        return new PageImpl<>(employeesDTO, pageable, employeesDTO.size());
    }

    //MODIFICA DATOS DEL EMPLEADO
    @Override
    public void updateEmployee(EmployeeDTOReq employeeDTO) throws Exception {
        var employeeBD = employeeUserRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new Exception("El id" + employeeDTO.getId() + " no existe"));

        //valida que el dni y username ingresados no existan en la bd
        //y si existen que solo pertenezacan al empleado encontrado
        this.validateIfExistsByDni(employeeDTO.getDni(), employeeBD.getDni());
        this.validateIfExistsByUsername(employeeDTO.getUsername(), employeeBD.getUsername());

        //asigna los nuevos datos y guarda el empleado
        var saveEmployee = modelMapper.map(employeeDTO, Employee.class);
        saveEmployee.setRoles(employeeBD.getRoles());
        saveEmployee.setEnable(true);
        saveEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employeeUserRepository.save(saveEmployee);
    }

    //ELIMINA EMPLEADO DE FORMA LÓGICA
    @Override
    public void deleteEmployee(Long employeeID) throws Exception {
        var employeeBD = employeeUserRepository.findById(employeeID)
                .orElseThrow(() -> new Exception("El id " + employeeID + " no existe"));
        employeeBD.setEnable(false);
        employeeUserRepository.save(employeeBD);
    }

    // Validaciones para el método UPDATE

    public void validateIfExistsByDni(String dniDTO, String dniBD) throws Exception {
        if (!dniDTO.equals(dniBD) && employeeUserRepository.existsByDni(dniDTO)) {
            throw new Exception("El dni " + dniDTO + " ya existe. Ingrese un nuevo dni");
        }
    }

    public void validateIfExistsByUsername(String usernameDTO, String usernameBD) throws Exception {
        if (!usernameDTO.equals(usernameBD) && employeeUserRepository.existsByUsername(usernameDTO)) {
            throw new Exception("El username " + usernameDTO + " ya existe. Ingrese un nuevo username");
        }
    }

    // Validaciones para el método SAVE

    //validar que el email, dni y username sean únicos al guardar un empleado
    public void validateEmployeeDataBeforeSaving(String dniDTO, String usernameDTO) throws Exception {
        if (employeeUserRepository.existsByUsername(usernameDTO)) {
            throw new Exception("El username " + usernameDTO + " ya existe. Ingrese un nuevo username");
        }
        if (employeeUserRepository.existsByDni(dniDTO)) {
            throw new Exception("El dni " + dniDTO + " ya existe. Ingrese un nuevo dni");
        }
    }
}
