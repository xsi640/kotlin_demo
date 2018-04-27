package com.suyang.mina.codec

import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolDecoder
import org.apache.mina.filter.codec.ProtocolEncoder


class CustomProtocolCodecFactory : ProtocolCodecFactory {
    private val decoder = CustomProtocolDecoder()
    private val encoder = CustomProtocolEncoder()

    @Throws(Exception::class)
    override fun getDecoder(ioSession: IoSession): ProtocolDecoder {
        return decoder
    }

    @Throws(Exception::class)
    override fun getEncoder(ioSession: IoSession): ProtocolEncoder {
        return encoder
    }
}