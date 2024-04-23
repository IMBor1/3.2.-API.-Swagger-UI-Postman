package ru.hogwarts.school.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Model.Avatar;
import ru.hogwarts.school.Service.AvatarService;

import java.util.List;

@RestController
public class AvatarTController {
    AvatarService avatarService;

    public AvatarTController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("avatar/all")
    ResponseEntity<List<Avatar>> getAvatars(@RequestParam("page") Integer pageNumber,
                                            @RequestParam("size") Integer pageSize) {
        List<Avatar> avatars = avatarService.getAllAvatars(pageNumber, pageSize);
        return ResponseEntity.ofNullable(avatars);
    }

}
