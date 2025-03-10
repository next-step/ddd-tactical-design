package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import kitchenpos.menu.tobe.infra.DefaultMenuAmountService
import kitchenpos.menu.tobe.infra.DefaultMenuProductPriceChangedEventListener
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

class MenuProductPriceChangedEventListenerTest {
    private lateinit var menuRepository: MenuRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var menuAmountService: MenuAmountService
    private lateinit var productClient: ProductClient

    private lateinit var menuProductPriceChangedEventListener: MenuProductPriceChangedEventListener

    @BeforeEach
    fun setUp() {
        menuRepository = FakeMenuRepository()
        productRepository = FakeProductRepository()
        productClient = DefaultProductClient(productRepository)
        menuAmountService = DefaultMenuAmountService(productClient)
        menuProductPriceChangedEventListener =
            DefaultMenuProductPriceChangedEventListener(menuRepository, menuAmountService)
    }

    @Test
    @DisplayName("Menu에 속한 MenuProduct의 ProductPrice가 변경될 때, MenuPrice>MenuAmount이면 NotDisplayed한다")
    fun changedProductPriceNotDisplayed() {
        // given productPrice 16000원
        val product = Fixtures.product(price = 16_000)
        productRepository.save(product)

        // given menuPrice 14000원, 16000원
        val menu_14000 = Fixtures.menu(
            menuAmountService = menuAmountService,
            price = 14_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = product.id, quantity = 1)
                )
            )
        )
        val menu_16000 = Fixtures.menu(
            menuAmountService = menuAmountService,
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
        menuProductPriceChangedEventListener.handle(ProductPriceChangedEvent(product.id))

        // then
        assertAll(
            { assertThat(menu_14000.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED) },
            { assertThat(menu_16000.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED) },
        )
    }
}
