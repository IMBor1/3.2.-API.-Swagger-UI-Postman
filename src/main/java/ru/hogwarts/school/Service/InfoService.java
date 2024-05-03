package ru.hogwarts.school.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    @Value("${server.port}")
    String port;

    public String getPort() {
        return port;
    }

}
