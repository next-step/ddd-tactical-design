package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe

class AmountTest : ExpectSpec({
    expect("금액이 0보다 작으면 예외가 발생한다") {
        val negativeAmount = -1

        shouldThrow<IllegalArgumentException> {
            Amount.valueOf(negativeAmount)
        }
    }

    expect("금액이 0이면 예외가 발생하지 않는다") {
        val zeroAmount = 0
        val actual = Amount.valueOf(zeroAmount)

        actual.value shouldBe zeroAmount.toBigDecimal()
    }
})
