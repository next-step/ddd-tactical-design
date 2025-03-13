package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import kitchenpos.menu.tobe.application.MenuProductPriceChangedListener
import kitchenpos.menu.tobe.infra.DefaultProductClient
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class MenuProductPriceChangedListenerTest {
    private lateinit var menuRepository: MenuRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var productClient: ProductClient

    private lateinit var menuProductPriceChangedListener: MenuProductPriceChangedListener

    @BeforeEach
    fun setUp() {
        menuRepository = FakeMenuRepository()
        productRepository = FakeProductRepository()
        productClient = DefaultProductClient(productRepository)
        menuProductPriceChangedListener =
            MenuProductPriceChangedListener(
                menuRepository,
                productClient
            )
    }

    @Test
    @DisplayName("Menu에 속한 MenuProduct의 ProductPrice가 변경될 때, MenuPrice>MenuAmount이면 NotDisplayed한다")
    fun changedProductPriceNotDisplayed() {
        // given productPrice 16000원
        val product = Fixtures.product(price = 16_000)
        productRepository.save(product)

        // given menuPrice 14000원, 16000원
        val menu_14000 = Fixtures.menu(
            productInfos = mapOf(product.id to ProductInfo(product.id, BigDecimal.valueOf(16_000))),
            price = 14_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = product.id, quantity = 1)
                )
            )
        )
        val menu_16000 = Fixtures.menu(
            productInfos = mapOf(product.id to ProductInfo(product.id, BigDecimal.valueOf(16_000))),
            price = 16_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = product.id, quantity = 1)
                )
            )
        )
        menuRepository.save(menu_14000)
        menuRepository.save(menu_16000)


        // when productPrice 15000원으로 변경
        product.changePrice(ProductPrice(BigDecimal.valueOf(15_000)))
        menuProductPriceChangedListener.handle(ProductPriceChangedEvent(product.id))

        // then 16000원이였던 메뉴만 NotDisplayed
        assertAll(
            { assertThat(menu_14000.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED) },
            { assertThat(menu_16000.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED) },
        )
    }
}
