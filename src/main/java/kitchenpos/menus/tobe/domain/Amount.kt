package kitchenpos.menus.tobe.domain

import java.math.BigDecimal

private val MIN_AMOUNT = BigDecimal.ZERO

@JvmInline
value class Amount(val value: BigDecimal) : Comparable<Amount> {
    init {
        require(value >= MIN_AMOUNT) { "금액은 $MIN_AMOUNT 이상이어야 합니다." }
    }

    override fun compareTo(other: Amount): Int {
        return value.compareTo(other.value)
    }

    operator fun plus(other: Amount): Amount {
        return Amount(value + other.value)
    }

    companion object {
        fun valueOf(value: Int): Amount {
            return Amount(value.toBigDecimal())
        }
    }
}
