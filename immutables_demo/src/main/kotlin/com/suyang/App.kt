package com.suyang

/**
 * 使用Immutables自动生成相关代码
 * https://immutables.github.io/immutable.html
 * 使用了annotation processor，需要配置gradle和idea
 *
 * 1.run gradle build
 * 2.run this
 */
fun main(args: Array<String>) {
    val value = ImmutableFoobarValue.builder()
            .foo(2)
            .bar("Bar")
            .addBuz(1, 3, 4)
            .build() // FoobarValue{foo=2, bar=Bar, buz=[1, 3, 4], crux={}}

    val foo = value.foo() // 2

    val buz = value.buz() // ImmutableList.of(1, 3, 4)

    println(foo)
    println(buz)


    val person = PersonBuilder()
            .name("Jim Boe")
            .address("P.O. box 0001, Lexington, KY")
            .build()

    println(person.name)
    println(person.address)
}