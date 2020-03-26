package ru.itis.services;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.FileInfoDto;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileInfoRepository;
import org.apache.commons.io.FilenameUtils;


import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private String storagePath;


    @Override
    public FileInfoDto save(MultipartFile file, Long ownerId) {
        String storageFileName = createStorageName(file.getOriginalFilename());
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(file.getOriginalFilename())
                .storageFileName(storageFileName.toString())
                .size(file.getSize())
                .type(file.getContentType())
                .url(storagePath + "/" + storageFileName)
                .build();

        fileInfoRepository.save(fileInfo);


        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath, storageFileName));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        FileInfoDto res = FileInfoDto.from(fileInfo);
        res.setOwnerId(ownerId);
        return res;
    }

    public FileInfo get(String name) {
        Optional<FileInfo> fileInfoCandidate = fileInfoRepository.find(name);
        return fileInfoCandidate.orElse(null);
    }

    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        FileInfo fileInfo = get(fileName);
        response.setContentType(fileInfo.getType());
        try {
            InputStream inputStream = new FileInputStream(new java.io.File(fileInfo.getUrl()));
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (
                IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    private String createStorageName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString();
        return newFileName + "." + extension;
    }

}
