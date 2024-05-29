package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import kitchenpos.products.tobe.adapter.FakeProductNameSlangChecker

class ProductNameValidatorTest : BehaviorSpec({
    val productNameValidator = ProductNameValidator(FakeProductNameSlangChecker())

    Given("상품 이름 검증 시") {
        When("욕설이 포함되어 있다면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                forAll(
                    row("비속어"),
                    row("욕설")
                ) { name ->
                    shouldThrowExactly<IllegalArgumentException> {
                        productNameValidator.requireNormalName(name)
                    }
                }
            }
        }
        When("포함되어 있지 않다면") {
            Then("예외를 발생시키지 않는다.") {
                shouldNotThrowAny {
                    productNameValidator.requireNormalName("정상 상품 이름")
                }
            }
        }
    }
})
