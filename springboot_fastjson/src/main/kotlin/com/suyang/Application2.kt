package com.suyang.mina

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter



@SpringBootApplication
open class Application2 : WebMvcConfigurer{
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>?) {
        super.configureMessageConverters(converters)
        val fastConverter = FastJsonHttpMessageConverter()

        val fastJsonConfig = FastJsonConfig()
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
        fastConverter.fastJsonConfig = fastJsonConfig

        converters?.add(fastConverter)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application2::class.java, *args)
}