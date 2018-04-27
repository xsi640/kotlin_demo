package com.suyang.mina.controller

import java.util.Arrays
import java.util.Date

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.web.context.WebApplicationContext

import com.suyang.Application
import com.suyang.domain.Student
import com.suyang.dao.StudentDao

import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*



@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(Application::class)])
@WebAppConfiguration
open class StudentControllerTest {
    lateinit var mockMvc: MockMvc
    open lateinit var mappingJackson2HttpMessageConverter: HttpMessageConverter<*>
    lateinit var student: Student

    @Autowired
    lateinit var studentDao: StudentDao

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    fun setConverters(converters: Array<HttpMessageConverter<*>>) {
        mappingJackson2HttpMessageConverter = Arrays.asList(*converters).stream()
                .filter { t -> t is MappingJackson2HttpMessageConverter }.findAny().orElse(null)

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter)
    }

    @Before
    @Throws(Exception::class)
    fun setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build()
        // 清除数据
        studentDao.deleteAll()

        // 准备测试数据
        student = Student()
        student.name = "张三"
        student.age = 18
        student.birthday = Date()
        studentDao.save(student)
    }

    @Test
    @Throws(Exception::class)
    fun findOneTest() {
        mockMvc.perform(get("/student/" + student.id)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(student.id)))
                .andExpect(jsonPath("$.name", `is`(student.name)))
                .andExpect(jsonPath("$.age", `is`(student.age)))
    }

    @Test
    @Throws(Exception::class)
    fun findAllTest() {
        mockMvc.perform(get("/student")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(1)))
    }

    @Test
    @Throws(Exception::class)
    fun createTest() {
        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "李四").param("age", "20").param("birthday", "2015-5-5"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`("李四")))
                .andExpect(jsonPath("$.age", `is`(20)))
    }

    @Test
    @Throws(Exception::class)
    fun modifyTest() {
        mockMvc.perform(put("/student").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "王五").param("age", "33").param("birthday", "2015-5-5").param("id", student.id.toString()))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(student.id)))
                .andExpect(jsonPath("$.name", `is`("王五")))
                .andExpect(jsonPath("$.age", `is`(33)))
                .andExpect(jsonPath("$.birthday", `is`("2015-05-05 00:00:00")))
    }

    @Test
    @Throws(Exception::class)
    fun deleteTest() {
        mockMvc.perform(delete("/student/" + student.id)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)

        mockMvc.perform(get("/student/" + student.id)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk).andExpect(content().string(""))
    }
}
