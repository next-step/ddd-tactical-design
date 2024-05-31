package kitchenpos.tobe.domain.menu.entity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kitchenpos.tobe.domain.menu.FakeMenuPugomalumClient
import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.product.exception.menu.MenuNameException
import kitchenpos.tobe.product.exception.menu.MenuPriceException
import tobe.domain.MenuFixtures.createMenu
import tobe.domain.MenuGroupFixtures.createMenuGroup
import tobe.domain.MenuProductsFixtures.createMenuProduct

class MenuTest : DescribeSpec() {
    init {
        describe("Menu 클래스의") {
            describe("changeProductPrice 메소드는") {
                context("상품 가격이 변경되었을 때") {
                    context("메뉴의 가격이, 메뉴구성상품의 가격과 수량의 곱을 합한 값보다 작으면") {
                        it("메뉴는 미노출 상태가 된다") {
                            val menu = createMenu()
                            val productId = menu.getMenuProducts().first().productId
                            menu.handleProductPriceChanged(
                                productId = productId,
                                price = 100.toBigDecimal(),
                            )

                            menu.displayed shouldBe false
                        }
                    }
                    context("메뉴의 가격이, 메뉴구성상품의 가격과 수량의 곱의 합한 값보다 크면") {
                        it("메뉴는 노출 여부가 유지된다.") {
                            val menu = createMenu()
                            val productId = menu.getMenuProducts().first().productId
                            menu.handleProductPriceChanged(
                                productId = productId,
                                price = 100000.toBigDecimal(),
                            )

                            menu.displayed shouldBe true
                        }
                    }
                }
            }
            describe("of 메소드는") {
                context("메뉴 이름으로 비속어가 주어졌을때") {
                    it("MenuNameException을 던진다") {
                        val menuPurgomalumClient = FakeMenuPugomalumClient()

                        shouldThrow<MenuNameException> {
                            MenuV2.of(
                                name = "욕1",
                                price = 16000.toBigDecimal(),
                                displayed = true,
                                menuGroup = createMenuGroup(),
                                menuProducts = mutableListOf(createMenuProduct()),
                                menuPurgomalumClient = menuPurgomalumClient,
                            )
                        }
                    }
                }

                context("메뉴구성상품의 가격과 수량의 곱을 합한 값이 메뉴의 가격보다 작을 때,") {
                    it("MenuPriceException을 던진다") {
                        val menuPurgomalumClient = FakeMenuPugomalumClient()

                        shouldThrow<MenuPriceException> {
                            MenuV2.of(
                                name = "테스트 메뉴 이름",
                                price = 20000.toBigDecimal(),
                                displayed = true,
                                menuGroup = createMenuGroup(),
                                menuProducts = mutableListOf(createMenuProduct()),
                                menuPurgomalumClient = menuPurgomalumClient,
                            )
                        }
                    }
                }
            }
        }
    }
}
