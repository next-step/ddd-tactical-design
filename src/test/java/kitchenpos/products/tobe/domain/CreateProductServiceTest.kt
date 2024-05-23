package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import kitchenpos.products.tobe.adapter.FakeSlangChecker
import java.math.BigDecimal

class CreateProductServiceTest : BehaviorSpec({
    val createProductService = CreateProductService(
        productPriceValidator = ProductPriceValidator(), productNameValidator = ProductNameValidator(FakeSlangChecker())
    )

    Given("상품 생성 시") {
        When("가격 검증을 통과하지 못하면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    createProductService.createProduct(
                        price = null,
                        name = "상품 이름",
                    )
                }
            }
        }
        When("이름 검증을 통과하지 못하면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    createProductService.createProduct(
                        price = BigDecimal.TWO,
                        name = "비속어",
                    )
                }
            }
        }
        When("모든 검증을 통과하면") {
            Then("입력한 가격과 이름으로 상품이 생성되고, id가 할당된다.") {
                shouldNotThrowAny {
                    createProductService.createProduct(
                        price = BigDecimal.TWO,
                        name = "정상 상품",
                    )
                }
            }
        }
    }
})
