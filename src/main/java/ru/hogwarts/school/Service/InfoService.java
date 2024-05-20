package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService {
    //устанавливаем порт
    @Value("${server.port}")
    String port;

    public String getPort() {
        return port;
    }

    //выбираем самый быстрый способ работы
    public List<Long> sumModdif() {
        Logger logger = LoggerFactory.getLogger(InfoService.class);
        long startTime1 = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_0000)
                .reduce(0, (a, b) -> a + b);
        long time1 = System.currentTimeMillis() - startTime1;
        logger.info(time1 + "");
        long startTime2 = System.currentTimeMillis();
// 9 из 10 раз этот метод самый быстрый.
        int sum2 = IntStream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_0000)
                .reduce(0, Integer::sum);
        long time2 = System.currentTimeMillis() - startTime2;
        logger.info(time2 + "");
        long startTime3 = System.currentTimeMillis();

        int sum3 = IntStream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_0000)
                .sum();

        long time3 = System.currentTimeMillis() - startTime3;
        logger.info(time3 + "");
        return List.of(time1, time2, time3);
    }
}
