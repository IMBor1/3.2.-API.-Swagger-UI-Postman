package ru.hogwarts.school.Service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Model.Avatar;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    //директория нахождения аватара
    @Value("${path.to.avatars.folder=c:/users/mm-b/avatars/1}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    //загрузка аватара
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method uploadAvatar");
        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "."
                + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    // получаем расширение
    private String getExtensions(String fileName) {
        logger.info("Was invoked method getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    //поиск аватара
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method findAvatar from avatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    //получаем все аватары постранично
    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("was invoked method getAllAvatars");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
