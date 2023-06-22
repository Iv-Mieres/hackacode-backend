package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.exception.DniExistsException;
import com.hackacode.themepark.exception.DniNotFoundException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.repository.IEmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IEmployeeRepository employeeUserRepository;

    //CREA UN EMPLEADO CON SU ROL Y SU JUEGO ASIGNADO
    @Override
    public void saveEmployee(EmployeeDTOReq employeeDTO) throws Exception {
        //valida que el dni sea único
        if (employeeUserRepository.existsByDni(employeeDTO.getDni())) {
            throw new DniExistsException("El dni " + employeeDTO.getDni() + " ya existe. Ingrese un nuevo dni");
        }
        //valida que el juego no sea nulo y que su id no exista en la BD
        if (!Objects.isNull(employeeDTO.getGame()) &&
                !employeeUserRepository.existsByGame_id(employeeDTO.getGame().getId())) {
            throw new EntityNotFoundException("El juego que intenta ingresar no existe. Ingrese un juego existente");
        }
        else {
            //convierte el DTO a Entity y lo guarda
            var employee = modelMapper.map(employeeDTO, Employee.class);
            employee.setEnable(true);
            employeeUserRepository.save(employee);
        }
    }

    //MUESTRA EMPLEADO POR ID
    @Override
    public EmployeeDTORes getEmployeeById(Long employeeId) throws IdNotFoundException {
        var employeeBD = employeeUserRepository.findById(employeeId)
                .orElseThrow(() -> new IdNotFoundException("El id " + employeeId + " no existe"));
        return modelMapper.map(employeeBD, EmployeeDTORes.class);
    }

    //MUESTRA EMPLEADO POR DNI
    @Override
    public EmployeeDTORes getEmployeeByDni(String employeeDni) throws DniNotFoundException {
        return modelMapper.map( employeeUserRepository.findByDni(employeeDni)
                .orElseThrow(() -> new DniNotFoundException("El dni " + employeeDni + " no existe")), EmployeeDTORes.class);
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
                .orElseThrow(() -> new IdNotFoundException("El id" + employeeDTO.getId() + " no existe"));

        //valida que el dni y username ingresados no existan en la bd
        //y si existen que solo pertenezacan al empleado encontrado
        this.validateIfExistsByDni(employeeDTO.getDni(), employeeBD.getDni());

        //asigna los nuevos datos y guarda el empleado
        var saveEmployee = modelMapper.map(employeeDTO, Employee.class);
        saveEmployee.setEnable(true);
        employeeUserRepository.save(saveEmployee);
    }

    //ELIMINA EMPLEADO DE FORMA LÓGICA
    @Override
    public void deleteEmployee(Long employeeID) throws IdNotFoundException {
        var employeeBD = employeeUserRepository.findById(employeeID)
                .orElseThrow(() -> new IdNotFoundException("El id " + employeeID + " no existe"));
        employeeBD.setEnable(false);
        employeeUserRepository.save(employeeBD);
    }

    // Validación de DNI para el método UPDATE

    public void validateIfExistsByDni(String dniDTO, String dniBD) throws Exception {
        if (!dniDTO.equals(dniBD) && employeeUserRepository.existsByDni(dniDTO)) {
            throw new Exception("El dni " + dniDTO + " ya existe. Ingrese un nuevo dni");
        }
    }

}
