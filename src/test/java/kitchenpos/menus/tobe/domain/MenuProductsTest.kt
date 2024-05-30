package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.UUID

private val DEFAULT_PRODUCT_ID = UUID.randomUUID()

class MenuProductsTest : BehaviorSpec({
    Given("메뉴 상품들을 생성할 때") {
        When("메뉴 상품이 비어있지 않으면") {
            val menuProduct = MenuProduct(1, DEFAULT_PRODUCT_ID, 1000)

            Then("메뉴 상품들이 생성된다") {
                val menuProducts = MenuProducts(mutableListOf(menuProduct))
                menuProducts.shouldBeInstanceOf<MenuProducts>()
            }
        }

        When("메뉴 상품이 주어지지 않은 경우") {
            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    MenuProducts(mutableListOf())
                }
            }
        }
    }
})
