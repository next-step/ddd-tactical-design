package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

private val Int.won: BigDecimal get() = this.toBigDecimal()

class ProductPriceTest : BehaviorSpec({
    given("상품 금액을 생성할 때") {
        `when`("금액이 0보다 작을 때") {
            val negativeValue = (-1).won

            then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    ProductPrice(negativeValue)
                }
            }
        }

        `when`("금액이 0일 때") {
            val zeroValue = 0.won

            then("생성된다") {
                val price = ProductPrice(zeroValue)
                price.value shouldBe zeroValue
            }
        }

        `when`("금액이 0보다 클 때") {
            val positiveValue = 1.won

            then("생성된다") {
                val price = ProductPrice(positiveValue)
                price.value shouldBe positiveValue
            }
        }
    }
})
