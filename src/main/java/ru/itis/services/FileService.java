package ru.itis.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.FileInfoDto;
import ru.itis.models.FileInfo;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    FileInfoDto save(MultipartFile multipartFile, Long ownerId);

    void writeFileToResponse(String fileName, HttpServletResponse response);
}
