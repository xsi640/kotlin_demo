package com.suyang

import org.immutables.value.Value

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PRIVATE)
interface Person {
    val name: String
    val address: String
}