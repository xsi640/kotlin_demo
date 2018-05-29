package com.suyang

import org.immutables.value.Value

@Value.Immutable
abstract class FoobarValue {
    abstract fun foo(): Int
    abstract fun bar(): String
    abstract fun buz(): List<Int>
    abstract fun crux(): Set<Long>
}
