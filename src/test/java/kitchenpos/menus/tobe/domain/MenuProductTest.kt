package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

private val DEFAULT_PRODUCT_ID = UUID.randomUUID()

class MenuProductTest : BehaviorSpec({
    Given("메뉴 상품을 생성할 때") {
        When("메뉴 상품의 수량이 0 이하인 경우") {
            val invalidQuantity = 0

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    MenuProduct(invalidQuantity, DEFAULT_PRODUCT_ID, 1000)
                }
            }
        }

        When("메뉴 상품의 수량이 1 이상인 경우") {
            val validQuantity = 1

            Then("메뉴 상품이 생성된다") {
                val menuProduct = MenuProduct(validQuantity, DEFAULT_PRODUCT_ID, 1000)

                menuProduct.productId shouldBe DEFAULT_PRODUCT_ID
                menuProduct.quantity shouldBe 1
                menuProduct.productPrice shouldBe 1000
            }
        }
    }
})
