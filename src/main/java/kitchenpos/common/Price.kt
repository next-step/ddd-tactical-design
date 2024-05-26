package kitchenpos.common

import java.math.BigDecimal

@JvmInline
value class Price(
    val value: BigDecimal
) {

    operator fun plus(other: Price): Price = Price(other.value.plus(value))

    operator fun compareTo(other: Price): Int = value.compareTo(other.value)
}

val ZERO: Price = Price(BigDecimal.ZERO)

fun BigDecimal.price() = Price(this)
