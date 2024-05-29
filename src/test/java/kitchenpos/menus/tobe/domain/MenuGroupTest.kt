package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class MenuGroupTest : BehaviorSpec({
    Given("메뉴 그룹을 생성할 때") {
        When("메뉴 그룹 이름이 주어진 경우") {
            Then("메뉴 그룹이 생성된다") {
                val name = "메뉴 그룹"
                val menuGroup = MenuGroup(name)
                menuGroup.name shouldBe name
            }
        }

        When("메뉴 그룹 이름이 주어지지 않은 경우") {
            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    MenuGroup("")
                }
            }
        }
    }
})
