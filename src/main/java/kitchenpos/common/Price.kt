package kitchenpos.common

import java.math.BigDecimal

@JvmInline
value class Price(val value: BigDecimal) {
    init {
        if (value < BigDecimal.ZERO) {
            throw IllegalArgumentException("가격은 0보다 작을 수 없습니다")
        }
    }

    operator fun plus(other: Price): Price = other.value.plus(value).price()

    operator fun compareTo(other: Price): Int = value.compareTo(other.value)

    operator fun times(other: Long): Price = value.multiply(BigDecimal.valueOf(other)).price()
}

val ZERO: Price = Price(BigDecimal.ZERO)

fun BigDecimal.price() = Price(this)
