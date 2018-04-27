package com.suyang.mina.codec

import com.suyang.mina.packet.Packet
import org.apache.commons.lang3.StringUtils
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolEncoderAdapter
import org.apache.mina.filter.codec.ProtocolEncoderOutput


class CustomProtocolEncoder : ProtocolEncoderAdapter() {

    @Throws(Exception::class)
    override fun encode(session: IoSession, message: Any, out: ProtocolEncoderOutput) {
        if (message !is Packet) {
            throw IllegalArgumentException("message type is not packet!!!")
        }
        val buffer = IoBuffer.allocate(1024).setAutoExpand(true)
        var byteMessage = ByteArray(0)
        if (!StringUtils.isEmpty(message.message)) {
            byteMessage = message.message.toByteArray(charset("UTF-8"))
        }
        buffer.putInt(4 + byteMessage.size + if (message.data == null) 0 else message.data!!.size)
        buffer.putInt(byteMessage.size)
        if (byteMessage != null && byteMessage.isNotEmpty()) {
            buffer.put(byteMessage, 0, byteMessage.size)
        }
        if (message.data != null && message.data!!.isNotEmpty()) {
            buffer.put(message.data, 0, message.data!!.size)
        }
        buffer.flip()
        out.write(buffer)
    }
}
