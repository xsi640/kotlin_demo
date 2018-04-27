package com.suyang.mina

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.*
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value = "/test-websocket")
@Component
class WebSocketService {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private lateinit var session: Session

    /**
     * 连接建立成功调用的方法 */
    @OnOpen
    fun onOpen(session: Session) {
        this.session = session
        webSocketSet.add(this)     //加入set中
        onlineCount++                //在线数加1
        logger.info("有新连接加入！当前在线人数为$onlineCount")
        try {
            sendMessage("ok")
        } catch (e: IOException) {
            logger.error(e.message, e)
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    fun onClose() {
        webSocketSet.remove(this)  //从set中删除
        onlineCount--           //在线数减1
        logger.info("有一连接关闭！当前在线人数为$onlineCount")
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    fun onMessage(message: String, session: Session) {
        logger.info("来自客户端的消息:$message")

        //群发消息
        for (item in webSocketSet) {
            try {
                item.sendMessage(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    fun onError(session: Session, error: Throwable) {
        logger.error(error.message, error)
    }


    @Throws(IOException::class)
    fun sendMessage(message: String) {
        this.session.basicRemote.sendText(message)
        //this.session.getAsyncRemote().sendText(message);
    }

    companion object {

        private val logger = LoggerFactory.getLogger(WebSocketService::class.java)

        //静态变量，用来记录当前在线连接数。
        @Volatile
        private var onlineCount = 0

        //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
        private val webSocketSet = CopyOnWriteArraySet<WebSocketService>()


        /**
         * 群发自定义消息
         */
        @Throws(IOException::class)
        fun sendInfo(message: String) {
            for (item in webSocketSet) {
                try {
                    item.sendMessage(message)
                } catch (e: IOException) {
                    continue
                }

            }
        }
    }
}
