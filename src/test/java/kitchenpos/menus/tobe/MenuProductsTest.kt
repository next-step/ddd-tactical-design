package kitchenpos.menus.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures.menuProduct
import kitchenpos.menus.tobe.domain.MenuProductQuantity
import kitchenpos.menus.tobe.domain.MenuProducts
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MenuProductsTest {

    @Test
    fun `메뉴상품 목록은 적어도 하나 이상의 항목 필요`() {
        assertThatThrownBy {
            MenuProducts(emptyList())
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("적어도 1개 이상의 상품을 등록해야합니다")
    }

    @Test
    fun `메뉴상품 목록의 총가격은 각 메뉴상품의 가격*수량의 합`() {
        val menuProducts = listOf(
            menuProduct(
                quantity = MenuProductQuantity(2),
                price = BigDecimal.valueOf(5000).price()
            ),
            menuProduct(
                quantity = MenuProductQuantity(3),
                price = BigDecimal.valueOf(1000).price()
            )
        ).let { MenuProducts(it) }

        assertThat(menuProducts.totalPrice).isEqualTo(BigDecimal.valueOf(13000).price())
    }
}
