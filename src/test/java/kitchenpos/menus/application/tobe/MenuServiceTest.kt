package kitchenpos.menus.application.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures
import kitchenpos.fixture.MenuFixtures.menuCreateRequest
import kitchenpos.fixture.ProductFixtures
import kitchenpos.menus.tobe.application.MenuService
import kitchenpos.menus.tobe.domain.DefaultMenuPriceValidatorService
import kitchenpos.menus.tobe.domain.MenuGroupRepository
import kitchenpos.menus.tobe.domain.MenuPriceValidatorService
import kitchenpos.menus.tobe.domain.MenuRepository
import kitchenpos.menus.tobe.dto.`in`.MenuProductCreateRequest
import kitchenpos.products.application.tobe.FakeProductRepository
import kitchenpos.products.tobe.domain.ProductRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MenuServiceTest {
    private val menuRepository: MenuRepository = FakeMenuRepository()
    private val menuGroupRepository: MenuGroupRepository = FakeMenuGroupRepository()
    private val productRepository: ProductRepository = FakeProductRepository()
    private val menuPriceValidatorService: MenuPriceValidatorService =
        DefaultMenuPriceValidatorService(productRepository)

    private val menuService: MenuService = MenuService(
        menuRepository,
        menuGroupRepository,
        FakeMenuNameValidatorService,
        menuPriceValidatorService,
        productRepository
    )

    @Test
    fun `메뉴의 가격이 메뉴 상품의 총 가격보다 클 경우 메뉴 생성 실패`() {
        val menuGroup = menuGroupRepository.save(MenuFixtures.menuGroup())
        val product = productRepository.save(ProductFixtures.product(BigDecimal.valueOf(10000)))

        assertThatThrownBy {
            menuService.createMenu(
                menuCreateRequest(
                    menuGroupId = menuGroup.id,
                    price = BigDecimal.valueOf(12000).price(),
                    menuProducts = listOf(
                        MenuProductCreateRequest(product.id, 1L)
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
    }
}
