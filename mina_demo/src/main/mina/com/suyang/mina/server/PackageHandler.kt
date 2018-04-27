package com.suyang.mina.server

import com.suyang.mina.packet.Packet
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IoSession
import java.nio.charset.Charset


class PackageHandler : IoHandlerAdapter() {

    @Throws(Exception::class)
    override fun messageReceived(session: IoSession?, message: Any?) {
        if (message == null)
            return
        if (message is Packet) {
            val packet = message as Packet?
            val msg = packet!!.message
            val data = packet.data
            val strData = String(data!!, Charset.forName("UTF-8"))
            println("receive:")
            println("msg:$msg")
            println("data:$strData")
        }
    }

    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession?) {
    }

    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
        try {
            session!!.closeNow()
        } catch (e: Exception) {
        }
    }
}
