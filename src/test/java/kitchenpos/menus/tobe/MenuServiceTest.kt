package kitchenpos.menus.tobe

import kitchenpos.common.price
import kitchenpos.fixture.MenuFixtures
import kitchenpos.fixture.MenuFixtures.menuCreateRequest
import kitchenpos.fixture.ProductFixtures
import kitchenpos.menus.tobe.application.MenuService
import kitchenpos.menus.tobe.domain.*
import kitchenpos.menus.tobe.dto.`in`.MenuProductCreateRequest
import kitchenpos.products.tobe.FakeProductRepository
import kitchenpos.products.tobe.domain.ProductRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MenuServiceTest {
    private val menuRepository: MenuRepository = FakeMenuRepository()
    private val menuGroupRepository: MenuGroupRepository = FakeMenuGroupRepository()
    private val productRepository: ProductRepository = FakeProductRepository()

    private val menuService: MenuService = MenuService(
        menuRepository,
        menuGroupRepository,
        FakeMenuNameValidator,
        productRepository
    )

    @Test
    fun `메뉴의 메뉴상품이 적어도 한개는 존재해야한다`() {
        val menuGroup = menuGroupRepository.save(MenuFixtures.menuGroup())
        assertThatThrownBy {
            menuService.createMenu(
                menuCreateRequest(
                    menuGroupId = menuGroup.id,
                    menuProducts = emptyList()
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("적어도 1개 이상의 상품을 등록해야합니다")
    }

    @Test
    fun `메뉴의 가격이 메뉴 상품의 총 가격보다 작거나 같아야한다`() {
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

    @Test
    fun `메뉴 상품의 수량이 0보다 작을 수 없다`() {
        val menuGroup = menuGroupRepository.save(MenuFixtures.menuGroup())
        val product = productRepository.save(ProductFixtures.product(BigDecimal.valueOf(10000)))

        assertThatThrownBy {
            menuService.createMenu(
                menuCreateRequest(
                    menuGroupId = menuGroup.id,
                    menuProducts = listOf(
                        MenuProductCreateRequest(product.id, -1L)
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴의 상품 수량은 0보다 크거나 같아야합니다")
    }

    @Test
    fun `메뉴의 가격이 메뉴상품의 총합 보다 클 경우 전시상태를 활성화 할 수 없다`() {
        //given
        val product = productRepository.save(ProductFixtures.product(BigDecimal.valueOf(10000)))
        val menuProducts = MenuProducts(listOf(MenuFixtures.menuProduct(product.id, MenuProductQuantity(1))))
        val menuPrice = BigDecimal.valueOf(30000).price()
        val menu = menuRepository.save(
            MenuFixtures.menu(
                price = menuPrice,
                menuProducts = menuProducts,
                displayStatus = false
            )
        )

        //when & then
        assertThatThrownBy {
            menuService.activateMenuDisplayStatus(menu.id)
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
    }

    @Test
    fun `메뉴 가격의 변경시에도 메뉴의 가격은 메뉴상품 가격의 총합보다 작거나 같아야한다`() {
        //given
        val product = productRepository.save(ProductFixtures.product(BigDecimal.valueOf(30000)))
        val menuProducts = MenuProducts(listOf(MenuFixtures.menuProduct(product.id, MenuProductQuantity(1))))
        val menuPrice = BigDecimal.valueOf(10000).price()
        val menu = menuRepository.save(
            MenuFixtures.menu(
                price = menuPrice,
                menuProducts = menuProducts,
                displayStatus = true
            )
        )

        //when & then
        val price = BigDecimal.valueOf(50000).price()
        assertThatThrownBy {
            menuService.changeMenuPrice(menu.id, price)
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
    }
}
