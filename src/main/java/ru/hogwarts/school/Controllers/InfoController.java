package ru.hogwarts.school.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Service.InfoService;

@RestController
public class InfoController {
    @Autowired
    InfoService infoService;

    //
    @GetMapping("/port")
    public String getNumberPort() {
        return infoService.getPort();
    }

    @GetMapping("/sum")
    public long sumModdif() {
        return infoService.sumModdif();
    }
}
