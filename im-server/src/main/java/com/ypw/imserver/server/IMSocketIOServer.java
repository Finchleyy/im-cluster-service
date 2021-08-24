package com.ypw.imserver.server;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author hongmeng
 * @date 2021/8/24
 */
@Component
@Data
public class IMSocketIOServer {


    @PostConstruct
    public void initSocketServer() {
        System.out.println("init server......");

    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy.........");
        //server.stop();
    }
}
