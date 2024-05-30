package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

private val Int.won: Amount get() = Amount.valueOf(this)

private val DEFAULT_PRODUCT_ID = UUID.randomUUID()
private val BLACK_WORDS = BlackWords(listOf())

private val 메뉴상품_1000원 = MenuProduct(1, DEFAULT_PRODUCT_ID, 1000)
private val 메뉴상품_2000원 = MenuProduct(2, DEFAULT_PRODUCT_ID, 1000)

class MenuTest : BehaviorSpec({
    Given("메뉴를 생성할 때") {
        When("메뉴 가격이 상품 금액의 합보다 작은 경우") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    Menu(displayedName, 2999.won, menuGroup, menuProducts)
                }
            }
        }

        When("메뉴 가격이 상품 금액과 같거나 크면") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))

            Then("메뉴가 생성된다") {
                val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)

                menu.name shouldBe displayedName
                menu.price shouldBe 3001.won
                menu.menuGroup shouldBe menuGroup
                menu.isDisplayed shouldBe false
            }
        }
    }

    Given("가격을 변경할 때") {
        When("메뉴 가격이 상품 금액의 합보다 작은 경우") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    menu.changePrice(2999.won)
                }
            }
        }

        When("메뉴 가격이 상품 금액과 같거나 크면") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)

            Then("메뉴 가격이 변경된다") {
                menu.changePrice(3002.won)

                menu.price shouldBe 3002.won
            }
        }
    }

    Given("메뉴를 숨길 때") {
        When("메뉴 가격이 상품 금액의 합보다 작은 경우") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)
            menuProducts.add(메뉴상품_1000원)

            Then("메뉴가 숨겨진다") {
                menu.hide()

                menu.isDisplayed shouldBe false
            }
        }

        When("메뉴 가격이 상품 금액과 같거나 크면") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)

            Then("메뉴가 숨겨진다") {
                menu.hide()

                menu.isDisplayed shouldBe false
            }
        }
    }

    Given("메뉴를 노출할 때") {
        When("메뉴 가격이 상품 금액의 합보다 작은 경우") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)
            menuProducts.add(메뉴상품_1000원)

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    menu.display()
                }
            }
        }

        When("메뉴 가격이 상품 금액과 같거나 크면") {
            val displayedName = DisplayedName("양념치킨", BLACK_WORDS)
            val menuGroup = MenuGroup("베스트 메뉴")
            val menuProducts = MenuProducts(mutableListOf(메뉴상품_1000원, 메뉴상품_2000원))
            val menu = Menu(displayedName, 3001.won, menuGroup, menuProducts)

            Then("메뉴가 노출된다") {
                menu.display()

                menu.isDisplayed shouldBe true
            }
        }
    }
})
