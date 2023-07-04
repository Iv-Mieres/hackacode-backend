package com.hackacode.themepark.util;


import com.hackacode.themepark.dto.request.AuthRequestDTOReq;
import com.hackacode.themepark.dto.response.AuthResponseDTORes;

public interface ILoginService {

    AuthResponseDTORes authenticate(AuthRequestDTOReq request);
}
