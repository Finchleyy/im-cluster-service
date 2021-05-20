package com.ypw.imserver;

import com.ypw.imserver.config.ImNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class ImServerApplication implements CommandLineRunner {
    @Autowired
    ImNettyServer imNettyServer;

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        imNettyServer.start(new InetSocketAddress(8888));
    }

}
