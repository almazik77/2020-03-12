package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import ru.itis.dto.FileInfoDto;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.FileService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FilesController {

    @Autowired
    private FileService fileService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView getUploadPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file_upload");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        FileInfoDto file = fileService.save(multipartFile, user.getId());
        return ResponseEntity.ok().body(file.getStorageFileName());
    }

    // localhost:8080/files/123809183093qsdas09df8af.jpeg

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        fileService.writeFileToResponse(fileName, response);
    }
}
