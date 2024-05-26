package kitchenpos.menus.tode.application

import io.kotest.assertions.throwables.shouldNotThrowAnyUnit
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import kitchenpos.menus.tode.adapter.FakeLoadProductAdapter
import java.util.*

class MenuProductSizeValidatorTest : BehaviorSpec({
    val fakeLoadProductAdapter = FakeLoadProductAdapter()
    val menuProductSizeValidator = MenuProductSizeValidator(loadProductPort = fakeLoadProductAdapter)

    beforeTest {
        fakeLoadProductAdapter.emptyListTrigger = false
    }

    Given("메뉴에 속한 상품의 크기 검증 시") {
        When("반환된 상품 길이와 요청된 id의 길이와 다르면") {
            Then("IllegalArgumentException 예외처리를 한다.") {
                fakeLoadProductAdapter.emptyListTrigger = true

                shouldThrowExactly<IllegalArgumentException> {
                    menuProductSizeValidator.requireProductSizeMatch(ids = listOf(UUID.randomUUID()))
                }
            }
        }
        When("반환된 상품 길이와 요청된 id의 길이가 같으면") {
            Then("예외 처리를 하지 않는다.") {
                shouldNotThrowAnyUnit {
                    menuProductSizeValidator.requireProductSizeMatch(ids = listOf(UUID.randomUUID()))
                }
            }
        }
    }
})
