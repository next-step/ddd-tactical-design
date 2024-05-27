package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

private val Int.won: BigDecimal get() = this.toBigDecimal()

class ProductPriceTest : BehaviorSpec({
    Given("상품 금액을 생성할 때") {
        When("금액이 0보다 작을 때") {
            val negativeValue = (-1).won

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    ProductPrice(negativeValue)
                }
            }
        }

        When("금액이 0일 때") {
            val zeroValue = 0.won

            Then("생성된다") {
                val price = ProductPrice(zeroValue)
                price.value shouldBe zeroValue
            }
        }

        When("금액이 0보다 클 때") {
            val positiveValue = 1.won

            Then("생성된다") {
                val price = ProductPrice(positiveValue)
                price.value shouldBe positiveValue
            }
        }
    }
})
