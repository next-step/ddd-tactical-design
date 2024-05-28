package kitchenpos.menus.tode.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import kitchenpos.menus.tode.adapter.FakeMenuNameSlangChecker

class MenuNameValidatorTest : BehaviorSpec({
    val menuNameValidator = MenuNameValidator(FakeMenuNameSlangChecker())

    Given("메뉴 이름 검증 시") {
        When("상품 이름이 null 이라면") {
            Then("IllegalArgumentException 예외 처리 한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    menuNameValidator.requireNormalName(name = null)
                }
            }
        }
        When("상품 이름에 비속어가 포함되어 있다면") {
            Then("IllegalArgumentException 예외 처리 한다.") {
                forAll(
                    row("욕설"), row("비속어")
                ) { name ->
                    shouldThrowExactly<IllegalArgumentException> {
                        menuNameValidator.requireNormalName(name = name)
                    }
                }
            }
        }
        When("상품 이름에 비속어가 포함되어 있지 않다면") {
            Then("예외 처리 하지 않는다.") {
                shouldNotThrowAny {
                    menuNameValidator.requireNormalName(name = "정상 메뉴 이름")
                }
            }
        }
    }
})
