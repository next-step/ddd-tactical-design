package kitchenpos.menus.application.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures.menu
import kitchenpos.fixture.MenuFixtures.menuProduct
import kitchenpos.menus.tobe.domain.MenuProducts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal


class MenuTest {
    @Test
    fun `메뉴 상품 가격은 상품 가격과 동기화를 위해 변경이 가능하다`() {
        //given
        val menuProduct = menuProduct(price = BigDecimal.valueOf(10000).price())
        val menu = menu(
            displayStatus = false,
            price = BigDecimal.valueOf(8000).price(),
            menuProducts = MenuProducts(listOf(menuProduct))
        )

        //when
        menu.changeMenuProduct(menuProduct.productId, BigDecimal.valueOf(5000).price())

        //then
        assertThat(menu.menuProducts.totalPrice).isEqualTo(BigDecimal.valueOf(5000).price())
    }

    @Test
    fun `전시 중인 메뉴의 가격이 메뉴상품의 총 가격이 보다 클 경우 전시 비활성 상태로 바뀐다`() {
        //given
        val menuProduct = menuProduct(price = BigDecimal.valueOf(10000).price())
        val menu = menu(
            displayStatus = true,
            price = BigDecimal.valueOf(8000).price(),
            menuProducts = MenuProducts(listOf(menuProduct))
        )

        //when
        menu.changeMenuProduct(menuProduct.productId, BigDecimal.valueOf(5000).price())

        //then
        assertThat(menu.menuProducts.totalPrice).isEqualTo(BigDecimal.valueOf(5000).price())
        assertThat(menu.displayStatus).isFalse()
    }
}
