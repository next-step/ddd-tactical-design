package kitchenpos.product.tobe.application

import java.math.BigDecimal
import kitchenpos.menu.tobe.application.MenuProductPriceChangedListener
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents

@RecordApplicationEvents
@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private lateinit var events: ApplicationEvents

    @SpyBean
    private lateinit var menuProductPriceChangedListener: MenuProductPriceChangedListener

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productClient: ProductClient

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Autowired
    private lateinit var menuGroupRepository: MenuGroupRepository

    @Autowired
    private lateinit var productService: ProductService

    @Test
    @DisplayName("ProductPrice를 변경하면 ProductPriceChangedEvent를 발행한다.")
    fun publishProductPriceChangedEvent() {
        // given
        val productId = productService.create(CreateProductReq("후라이드치킨", BigDecimal.valueOf(16000))).id
        val changeProductPriceReq = ChangeProductPriceReq(BigDecimal.valueOf(17000))

        // when
        productService.changePrice(productId, changeProductPriceReq)

        // then
        assertThat(events.stream(ProductPriceChangedEvent::class.java)).hasSize(1)
    }

    @Test
    @DisplayName("`Product price`를 변경 할 때 `Product`를 포함한 `Menu`들 중 `Menu Price > Menu Amount`인 `Menu`는 `Not Displayed`된다")
    fun changePriceMenuNotDisplayed() {
        // given
        val menuGroup = menuGroupRepository.save(Fixtures.menuGroup(name = "치킨"))
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))
        val menu = menuRepository.save(
            Fixtures.menu(
                productClient = productClient,
                menuGroup = menuGroup,
                name = "양념치킨 세트",
                price = 32000,
                display = MenuDisplay.DISPLAYED,
                menuProducts = MenuProducts(listOf(Fixtures.menuProduct(productId = product.id, quantity = 2)))
            )
        )

        // when
        productService.changePrice(product.id, ChangeProductPriceReq(BigDecimal.valueOf(15000)))

        // then
        val changedProduct = productRepository.findById(product.id).orElseThrow()
        val changedMenu = menuRepository.findById(menu.id).orElseThrow();
        assertAll({
            assertThat(changedProduct.productPrice.price).isEqualTo("15000.00".toBigDecimal())
            assertThat(changedMenu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
        })
    }

    @Test
    @DisplayName("`Product price`를 변경 할 때 `Menu displayed`변경에 실패하면 롤백된다")
    fun changePriceRollback() {
        // given
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))
        doThrow(RuntimeException::class.java)
            .`when`(menuProductPriceChangedListener)
            .handle(any())

        // when
        assertThatThrownBy {
            productService.changePrice(product.id, ChangeProductPriceReq(BigDecimal.valueOf(15000)))
        }

        // then
        val changedProduct = productRepository.findById(product.id).orElseThrow()
        assertThat(changedProduct.productPrice.price).isEqualTo(("16000.00").toBigDecimal())
    }

}
