package kitchenpos.menus.tode.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import java.math.BigDecimal

class MenuPriceValidatorTest : BehaviorSpec({
    Given("메뉴 가격 검증 시") {
        When("가격 정보가 null 이라면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    MenuPriceValidator.requireNormalPrice(price = null)
                }
            }
        }
        When("가격 정보가 음수라면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    MenuPriceValidator.requireNormalPrice(price = BigDecimal.valueOf(-1))
                }
            }
        }
        When("가격 정보가 0이상의 값이라면") {
            Then("예외 처리 하지 않는다.") {
                forAll(
                    row(BigDecimal.ZERO),
                    row(BigDecimal.ONE),
                    row(BigDecimal.valueOf(1000L))
                ) { price ->
                    shouldNotThrowAny {
                        MenuPriceValidator.requireNormalPrice(price = price)
                    }
                }
            }
        }
    }

    Given("메뉴 가격과 상품 가격의 합 검증 시") {
        When("메뉴 가격이 상품 가격의 합보다 크다면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    MenuPriceValidator.requireMenuPriceUnderSum(
                        menuPrice = BigDecimal.TWO,
                        sum = BigDecimal.ONE,
                    )
                }
            }
        }
        When("메뉴 가격이 상품 가격의 합보다 작거나 같다면") {
            Then("예외 처리 하지 않는다.") {
                forAll(
                    row(BigDecimal.ONE, BigDecimal.TWO),
                    row(BigDecimal.valueOf(100L), BigDecimal.valueOf(101L))
                ) { menuPrice, sum ->
                    MenuPriceValidator.requireMenuPriceUnderSum(
                        menuPrice = menuPrice,
                        sum = sum,
                    )
                }
            }
        }
    }
})
