package com.suyang.mina.server

import com.suyang.mina.codec.CustomProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.io.IOException
import java.net.InetSocketAddress

class AppServer

@Throws(IOException::class)
fun main(args: Array<String>) {
    // 创建一个非阻塞的Server端socket，用NIO
    val acceptor = NioSocketAcceptor()
    // 创建接受数据的过滤器
    // TextLineCodecFactory lineCodec = new
    // TextLineCodecFactory(Charset.forName("UTF-8"));
    // lineCodec.setDecoderMaxLineLength(10 * 1024 * 1024);
    // lineCodec.setEncoderMaxLineLength(10 * 1024 * 1024);
    //acceptor.filterChain.addLast("logger", LoggingFilter())
    acceptor.filterChain.addLast("codec", ProtocolCodecFilter(CustomProtocolCodecFactory()))
    // 指定业务逻辑处理器
    acceptor.handler = PackageHandler()
    // 设置端口号
    acceptor.setDefaultLocalAddress(InetSocketAddress(12345))
    println("启动监听 port:" + 12345)
    acceptor.bind() // 启动监听
}