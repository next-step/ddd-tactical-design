package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ProductNameTest : BehaviorSpec({
    Given("상품 이름을 생성할 떄") {
        When("이름이 1자 미만일 때") {
            val emptyName = ""

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    ProductName(emptyName)
                }
            }
        }

        When("이름이 1자일 때") {
            val oneCharacterName = "a"

            Then("생성된다") {
                val name = ProductName(oneCharacterName)
                name.value shouldBe oneCharacterName
            }
        }

        When("이름이 255자일 때") {
            val twoHundredFiftyFiveCharactersName = "a".repeat(255)

            Then("생성된다") {
                val name = ProductName(twoHundredFiftyFiveCharactersName)
                name.value shouldBe twoHundredFiftyFiveCharactersName
            }
        }

        When("이름이 255자 초과일 때") {
            val twoHundredFiftySixCharactersName = "a".repeat(256)

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    ProductName(twoHundredFiftySixCharactersName)
                }
            }
        }
    }
})
