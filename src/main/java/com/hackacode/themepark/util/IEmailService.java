package com.hackacode.themepark.util;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;
import com.hackacode.themepark.dto.request.ResetPasswordDTOReq;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Token;
import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendEmail(Token token) throws MessagingException;

    void resetPassword(ResetPasswordDTOReq passDTO) throws Exception;
}
