package com.suyang.mina.codec

import com.suyang.mina.packet.Packet
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.AttributeKey
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolDecoderAdapter
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import java.nio.charset.Charset
import java.util.*


class CustomProtocolDecoder : ProtocolDecoderAdapter() {

    private val CONTEXT = AttributeKey(javaClass, "context")

    @Throws(Exception::class)
    override fun decode(session: IoSession, ioBuffer: IoBuffer, out: ProtocolDecoderOutput) {
        val ctx = getContext(session)
        var matchCount = ctx.matchLength// 目前已获取的数据
        var length = ctx.length// 数据总长度
        val buffer = ctx.buffer// 数据存入buffer

        if (length == 0) {
            length = ioBuffer.int
            ctx.length = length
            matchCount = ioBuffer.remaining()
            ctx.matchLength = matchCount
        } else {
            matchCount += ioBuffer.remaining()
            ctx.matchLength = matchCount
        }

        if (ioBuffer.hasRemaining()) {// 如果buff中还有数据
            buffer!!.put(ioBuffer)// 添加到保存数据的buffer中
            if (matchCount >= length) {// 如果已经发送的数据的长度>=目标数据的长度,则进行解码
                buffer.flip()
                if (buffer.remaining() > 0) {
                    decodePacket(buffer, out, length)
                }
                ctx.reset()
            } else {
                ctx.buffer = buffer
            }
        }
    }

    private fun decodePacket(ioBuffer: IoBuffer, out: ProtocolDecoderOutput, size: Int) {
        try {
            val msgLen = ioBuffer.int
            if (msgLen >= 0) {
                val packet = Packet()
                if (msgLen > 0) {
                    val msgBytes = ByteArray(msgLen)
                    ioBuffer.get(msgBytes, 0, msgBytes.size)
                    packet.message = String(msgBytes, Charset.forName("UTF-8"))
                    var d = CustomProtocolDecoder()
                }
                if (ioBuffer.hasRemaining()) {
                    val data = ByteArray(ioBuffer.remaining())
                    ioBuffer.get(data, 0, data.size)
                    packet.data = data
                }
                out.write(packet)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getContext(session: IoSession): Context {
        var ctx: Context? = session.getAttribute(CONTEXT) as Context
        if (ctx == null) {
            ctx = Context()
            session.setAttribute(CONTEXT, ctx)
        }
        return ctx
    }

    private inner class Context {
        var buffer: IoBuffer? = null
        var length = 0
        var matchLength = 0

        init {
            buffer = IoBuffer.allocate(1024).setAutoExpand(true)
        }

        fun reset() {
            this.buffer!!.clear()
            this.length = 0
            this.matchLength = 0
        }
    }
}