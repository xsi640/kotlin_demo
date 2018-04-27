package com.suyang.mina

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import org.springframework.context.annotation.Bean


@SpringBootApplication
open class Application{

    /**
     * 使用fastjson默认序列化
     */
    @Bean
    open fun fastJsonHttpMessageConverters(): HttpMessageConverters {
        val fastConverter = FastJsonHttpMessageConverter()
        val fastJsonConfig = FastJsonConfig()
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
        fastConverter.fastJsonConfig = fastJsonConfig
        return HttpMessageConverters(fastConverter)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
