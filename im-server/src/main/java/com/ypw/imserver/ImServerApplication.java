package com.ypw.imserver;

import com.corundumstudio.socketio.SocketIOServer;
import com.ypw.imserver.config.ImNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class ImServerApplication implements CommandLineRunner {
    @Autowired
    ImNettyServer imNettyServer;
    @Autowired
    SocketIOServer socketIOServer;

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //imNettyServer.start(new InetSocketAddress(8888));
        socketIOServer.start();
    }

    @PreDestroy
    public void shutdown() {
        socketIOServer.stop();
        log.warn("socketIOServer.stop()");
    }
}
