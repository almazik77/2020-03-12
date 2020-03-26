package ru.itis.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.FileInfoDto;
import ru.itis.services.MailService;

import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class UploadingFileAspect {

    @Autowired
    private MailService mailService;

    @After(value = "execution(* ru.itis.controllers.FilesController.uploadFile(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* ru.itis.services.FileServiceImpl.save(..))", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        FileInfoDto fileInfoDto = (FileInfoDto) returnValue;
        log.info("notifying " + fileInfoDto);
        mailService.uploadNotify(fileInfoDto);
    }
}
