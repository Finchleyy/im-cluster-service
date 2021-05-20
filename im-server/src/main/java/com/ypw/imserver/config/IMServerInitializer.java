package com.ypw.imserver.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author hongmeng
 * @since 2021/5/19
 */
public class IMServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 服务器读操作空闲(秒)
     */
    private final static int readerIdleTime = 0;
    /**
     * 服务器写操作空闲(秒)
     */
    private final static int writerIdleTime = 0;
    /**
     * 服务器全部操作空闲(秒)
     */
    private final static int allIdleTime = 30;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 心跳检测，如果在N分钟时没有向服务端发送读写心跳(ALL)，则主动断开 0代表不处理
        socketChannel.pipeline().addLast(
                new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.SECONDS));
        // 权鉴处理器
        //socketChannel.pipeline().addLast(new AuthHandler());
        // 心跳处理器
        //socketChannel.pipeline().addLast(new HeartbeatHandler());
        // 业务处理器
        //socketChannel.pipeline().addLast(new BusinessHandler());
    }
}
