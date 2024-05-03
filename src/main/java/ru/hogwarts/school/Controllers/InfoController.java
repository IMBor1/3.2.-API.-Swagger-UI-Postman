package ru.hogwarts.school.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    String numberPort;

    @GetMapping("/port")
    public String getNumberPort() {
        return numberPort;
    }
}
