package kitchenpos.menu.tobe.application

import java.math.BigDecimal
import java.util.*
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.application.dto.CreateMenuProductReq
import kitchenpos.menu.tobe.application.dto.CreateMenuReq
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.infra.FakeMenuGroupRepository
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.product.tobe.infra.FakeProfanities
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class MenuServiceTest {
    private lateinit var menuService: MenuService

    private lateinit var menuRepository: MenuRepository
    private lateinit var menuGroupRepository: MenuGroupRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var profanities: Profanities

    private lateinit var menuGroupId: UUID
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        menuRepository = FakeMenuRepository()
        menuGroupRepository = FakeMenuGroupRepository()
        productRepository = FakeProductRepository()
        profanities = FakeProfanities()
        menuService = MenuService(menuRepository, menuGroupRepository, productRepository, profanities)

        menuGroupId = menuGroupRepository.save(Fixtures.menuGroup()).id!!
        product = productRepository.save(Fixtures.product(name = "후라이드", price = 16_000))
    }

    @DisplayName("메뉴를 등록한다")
    @Test
    fun create() {
        // given
        val request = CreateMenuReq(
            menuGroupId = menuGroupId,
            name = "후라이드",
            price = 16_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = listOf(
                CreateMenuProductReq(
                    seq = 1,
                    productId = product.id!!,
                    quantity = 1
                )
            )
        )

        // when
        val menuResp = menuService.create(request)

        // then
        assertThat(menuResp).isNotNull
        assertAll(
            { assertThat(menuResp.id).isNotNull() },
            { assertThat(menuResp.name).isEqualTo("후라이드") },
            { assertThat(menuResp.price).isEqualTo(BigDecimal.valueOf(16000)) },
            { assertThat(menuResp.display).isEqualTo(MenuDisplay.DISPLAYED) },
            { assertThat(menuResp.menuGroup.id).isEqualTo(menuGroupId) },
            { assertThat(menuResp.menuProducts).hasSize(1) }
        )
    }


}
