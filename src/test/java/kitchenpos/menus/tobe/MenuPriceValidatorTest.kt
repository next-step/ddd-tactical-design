package kitchenpos.menus.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures
import kitchenpos.fixture.MenuFixtures.menu
import kitchenpos.menus.tobe.domain.MenuProductQuantity
import kitchenpos.menus.tobe.domain.MenuProducts
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MenuPriceValidatorTest {
    @Test
    fun `전시 중인 메뉴의 가격보다 메뉴상품의 가격의 총합이 더 클 경우 예외발생`() {
        assertThatThrownBy {
            menu(
                price = BigDecimal.valueOf(20000).price(),
                menuProducts = MenuProducts(
                    listOf(
                        MenuFixtures.menuProduct(
                            quantity = MenuProductQuantity(1),
                            price = BigDecimal.valueOf(7000).price()
                        ), MenuFixtures.menuProduct(
                            quantity = MenuProductQuantity(2),
                            price = BigDecimal.valueOf(6000).price()
                        )
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴상품 가격의 총합보다 메뉴의 가격이 클 수 없습니다")
    }
}
