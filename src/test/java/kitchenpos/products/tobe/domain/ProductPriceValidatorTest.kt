package kitchenpos.products.tobe.domain

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import java.math.BigDecimal

class ProductPriceValidatorTest : BehaviorSpec({
    val productPriceValidator = ProductPriceValidator()

    Given("상품 가격 검증 시") {
        When("가격이 null이 라면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    productPriceValidator.requireNormalPrice(null)
                }
            }
        }
        When("가격이 0 미만 이라면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    productPriceValidator.requireNormalPrice(BigDecimal.valueOf(-1L))
                }
            }
        }
        When("가격이 null이 아니고, 0 이상이라면") {
            Then("예외를 던지지 않는다.") {
                assertSoftly {
                    shouldNotThrowAny {
                        productPriceValidator.requireNormalPrice(BigDecimal.ZERO)
                    }
                    shouldNotThrowAny {
                        productPriceValidator.requireNormalPrice(BigDecimal.TWO)
                    }
                }
            }
        }
    }
})
