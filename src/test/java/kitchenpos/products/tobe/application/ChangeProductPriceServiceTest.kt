package kitchenpos.products.tobe.application

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kitchenpos.products.domain.Product
import kitchenpos.products.tobe.domain.FakeProductRepository
import java.math.BigDecimal
import java.util.*

class ChangeProductPriceServiceTest : BehaviorSpec({
    val productRepository = FakeProductRepository()
    val changeProductPriceService = ChangeProductPriceService(
        productRepository = productRepository
    )

    beforeTest{
        productRepository.deleteAll()
    }

    Given("상품 가격 변경 시") {
        When("가격 검증에 실패하면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    changeProductPriceService.changeProductPrice(
                        productId = UUID.randomUUID(),
                        price = null,
                    )
                }
            }
        }

        When("상품을 찾지 못하면") {
            Then("NoSuchElementException 예외 처리를 한다.") {
                shouldThrowExactly<NoSuchElementException> {
                    changeProductPriceService.changeProductPrice(
                        productId = UUID.randomUUID(),
                        price = BigDecimal.TWO,
                    )
                }
            }
        }

        When("가격 검증에 통과하고, 존재하는 상품이라면") {
            Then("찾아온 상품의 가격을 업데이트 한다.") {
                val product = productRepository.save(Product().apply { id })

                val result = changeProductPriceService.changeProductPrice(
                    productId = product.id, price = BigDecimal.TWO
                )
                result.price shouldBe BigDecimal.TWO
            }
        }
    }
})
