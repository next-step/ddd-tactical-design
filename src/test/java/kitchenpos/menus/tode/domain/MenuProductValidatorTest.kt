package kitchenpos.menus.tode.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import kitchenpos.menus.domain.MenuProduct

class MenuProductValidatorTest : BehaviorSpec({
    Given("메뉴에 속한 상품 요청 검증 시") {
        When("요청이 null 이라면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    MenuProductValidator.requireNormalMenuProduct(menuProductRequest = null)
                }
            }
        }
        When("요청이 비어있다면") {
            Then("IllegalArgumentException 예외 처리를 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    MenuProductValidator.requireNormalMenuProduct(menuProductRequest = emptyList())
                }
            }
        }
        When("요청 비어있지 않다면") {
            Then("예외가 발생되지 않는다.") {
                shouldNotThrowAny {
                    MenuProductValidator.requireNormalMenuProduct(menuProductRequest = listOf(MenuProduct()))
                }
            }
        }
    }
})
