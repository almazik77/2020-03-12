package ru.itis.services;

import ru.itis.dto.FileInfoDto;
import ru.itis.models.Mail;

public interface MailService {
    void sendConfirmEmail(Mail mail);
    void uploadNotify(FileInfoDto fileInfoDto);
}
