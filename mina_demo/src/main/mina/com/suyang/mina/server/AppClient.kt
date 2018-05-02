package com.suyang.mina.server

import com.suyang.mina.codec.CustomProtocolCodecFactory
import com.suyang.mina.packet.Packet
import org.apache.mina.core.RuntimeIoException
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector
import java.net.ConnectException
import java.net.InetSocketAddress

class AppClient : IoHandlerAdapter() {

    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession?) {
        println("sessionOpened")
    }

    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession?) {
        println("sessionClosed")
    }

    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession?, status: IdleStatus?) {
        println("sessionIdle")
        try {
            releaseSession(session!!)
        } catch (e: RuntimeIoException) {
        }

    }

    @Throws(Exception::class)
    override fun messageReceived(session: IoSession?, message: Any?) {
        println("Receive Server message " + message!!)

        super.messageReceived(session, message)

        releaseSession(session!!)
    }

    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
        println("exceptionCaught")
        cause.printStackTrace()
        releaseSession(session!!)
    }

    @Throws(Exception::class)
    override fun messageSent(session: IoSession?, message: Any?) {
        println("messageSent")
        super.messageSent(session, message)
    }

    private fun releaseSession(session: IoSession) {
        println("releaseSession")
        if (session.isConnected) {
            session.closeOnFlush()
        }
    }
}

fun main(args: Array<String>) {
    val connector = NioSocketConnector()
    connector.filterChain.addLast("logger", LoggingFilter())
    connector.filterChain.addLast("codec", ProtocolCodecFilter(CustomProtocolCodecFactory()))
    connector.handler = AppClient()

    val addr = InetSocketAddress("127.0.0.1", 12345)
    val cf = connector.connect(addr)
    try {
        cf.awaitUninterruptibly()

        val packet = Packet()
        packet.message = "消息123"
        packet.data = "".toByteArray(charset("UTF-8"))
        cf.session.write(packet)
        println("send message")
    } catch (e: RuntimeIoException) {
        if (e.cause is ConnectException) {
            try {
                if (cf.isConnected) {
                    cf.session.closeNow()
                }
            } catch (e1: RuntimeIoException) {
            }

        }
    }

}
