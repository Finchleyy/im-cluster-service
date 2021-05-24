package com.ypw.imserver.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author hongmeng
 * @since 2021/5/19
 */
@Component
@Slf4j
public class ImNettyServer {
    /**
     * 一个主线程组
     * 用于接收客户端的链接，但不做任何处理
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    /**
     * N个工作线程组
     * 从线程组，主线程组会把任务转给从线程组进行处理
     */
    private EventLoopGroup workGroup = new NioEventLoopGroup(512);

    /**
     * 启动netty服务
     *
     * @param socketAddress
     */
    public void start(InetSocketAddress socketAddress) {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new IMServerInitializer())
                .localAddress(socketAddress)
                //临时存放已完成三次握手的请求的队列的最大长度  默认50
                .option(ChannelOption.SO_BACKLOG, 1024)
                //两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            //绑定端口,开始接收进来的连接
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            log.info("服务器启动开始监听端口: {}", socketAddress.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭主线程组和工作线程组
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 关闭netty服务
     */
    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workGroup.shutdownGracefully().syncUninterruptibly();
        log.info("关闭 Netty 成功");
    }
}
