package kitchenpos.tobe.domain.menu.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import tobe.domain.MenuFixtures.createMenu

class MenuTest : DescribeSpec() {
    init {
        describe("Menu 클래스의") {
            describe("changeProductPrice 메소드는") {
                context("상품 가격이 변경되었을 때") {
                    context("메뉴의 가격이, 메뉴 구성 상품의 가격의 합보다 작으면") {
                        it("메뉴는 미노출 상태가 된다") {
                            val menu = createMenu()
                            val productId = menu.getMenuProducts().first().productId
                            menu.changeProductPrice(
                                productId = productId,
                                price = 100.toBigDecimal(),
                            )

                            menu.displayed shouldBe false
                        }
                    }
                    context("메뉴의 가격이, 메뉴 구성 상품의 가격의 합보다 크면") {
                        it("메뉴는 노출 여부가 유지된다.") {
                            val menu = createMenu()
                            val productId = menu.getMenuProducts().first().productId
                            menu.changeProductPrice(
                                productId = productId,
                                price = 100000.toBigDecimal(),
                            )

                            menu.displayed shouldBe true
                        }
                    }
                }
            }
        }
    }
}
