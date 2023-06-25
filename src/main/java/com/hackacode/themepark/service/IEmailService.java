package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;

public interface IEmailService {

    void sendEmail(RecoverPasswordDTOReq user);
}
