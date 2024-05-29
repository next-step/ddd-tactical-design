package kitchenpos.menus.application.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures
import kitchenpos.fixture.ProductFixtures
import kitchenpos.menus.tobe.domain.*
import kitchenpos.products.tobe.domain.ProductRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

class MenuPriceValidatorServiceTest {
    private val productRepository: ProductRepository = mock(ProductRepository::class.java)

    private val menuPriceValidatorService: MenuPriceValidatorService =
        DefaultMenuPriceValidatorService(productRepository)

    @Test
    fun `전시 중인 메뉴의 가격보다 메뉴상품의 가격의 총합이 더 클 경우 예외발생`() {
        val `2만원` = BigDecimal.valueOf(20000).price()
        val `7천원 상품` = ProductFixtures.product(BigDecimal.valueOf(7000))
        val `6천원 상품` = ProductFixtures.product(BigDecimal.valueOf(6000))

        val `전시 중인 2만원 메뉴` = MenuFixtures.menu(
            price = `2만원`,
            menuProducts = MenuProducts(
                listOf(
                    MenuFixtures.menuProduct(
                        productId = `7천원 상품`.id,
                        quantity = MenuProductQuantity(1)
                    ), MenuFixtures.menuProduct(
                        productId = `6천원 상품`.id,
                        quantity = MenuProductQuantity(2)
                    )
                )
            )
        )

        `when`(productRepository.findAllByIdIn(anyList())).thenReturn(listOf(`7천원 상품`, `6천원 상품`))

        //7000*1 + 6000*2 < 20000
        assertThatThrownBy { menuPriceValidatorService.validate(`전시 중인 2만원 메뉴`) }
            .isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴상품 가격의 총합보다 메뉴의 가격이 클 수 없습니다")
    }
}
