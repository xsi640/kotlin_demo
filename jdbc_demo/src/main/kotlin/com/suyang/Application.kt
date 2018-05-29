package com.suyang

import com.suyang.dao.impl.StudentDaoImpl
import com.suyang.domain.Student
import java.util.*

val studentDao = StudentDaoImpl()

fun main(args: Array<String>) {
    var list = LinkedList<Student>()
    for (i in 1..1000) {
        var s = Student(
                0,
                RandomString.getRandomString(50),
                Random().nextInt(100),
                Date(),
                Random().nextInt(10))
        list.add(s)
    }

    var s = System.currentTimeMillis()

    println("before count:${studentDao.count()}")
    println("isnerting")
    try {
        studentDao.saveMany(list)
    } catch (e: Exception) {
        println("stop!!")
    }
    var e = System.currentTimeMillis()
    println("end count:${studentDao.count()}")
    println("finish...time:${e - s}")
}


object RandomString {

    /**
     * 获取随机字符串 a-z,A-Z,0-9
     *
     * @param length
     * 长度
     * @return
     */
    fun getRandomString(length: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuffer()

        for (i in 0 until length) {
            val number = random.nextInt(62)// [0,62)
            sb.append(str[number])
        }
        return sb.toString()
    }

    /**
     * JAVA获得0-9,a-z,A-Z范围的随机数
     *
     * @param length
     * 随机数长度
     * @return String
     */
    fun getRandomChar(length: Int): String {
        val chr = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        val random = Random()
        val buffer = StringBuffer()
        for (i in 0 until length) {
            buffer.append(chr[random.nextInt(62)])
        }
        return buffer.toString()
    }

}