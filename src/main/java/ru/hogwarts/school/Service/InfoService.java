package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {
    @Value("${server.port}")
    String port;

    public String getPort() {
        return port;
    }

    public long sumModdif() {
        Logger logger = LoggerFactory.getLogger(InfoService.class);
        long startTime = System.currentTimeMillis();

        int sum = Stream.iterate(1, a -> a + 1)

                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long time = System.currentTimeMillis() - startTime;
        logger.info(time + "");
        long startTime1 = System.currentTimeMillis();

        int sum1 = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long time1 = System.currentTimeMillis() - startTime1;
        logger.info(time1 + "");
        return time;
    }
}
