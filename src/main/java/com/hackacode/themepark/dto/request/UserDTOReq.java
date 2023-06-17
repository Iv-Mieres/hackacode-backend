package com.hackacode.themepark.dto.request;

import com.hackacode.themepark.dto.response.RoleDTORes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOReq {

    private Long id;
    private String username;
    private String password;
    private EmployeeDTOReq employee;
    private List<RoleDTORes> roles;
}
