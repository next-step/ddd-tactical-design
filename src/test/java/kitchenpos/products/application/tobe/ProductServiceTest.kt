package kitchenpos.products.application.tobe

import kitchenpos.common.price
import kitchenpos.menus.tobe.application.MenuService
import kitchenpos.menus.tobe.domain.MenuGroupService
import kitchenpos.menus.tobe.domain.MenuRepository
import kitchenpos.menus.tobe.domain.findByIdOrNull
import kitchenpos.menus.tobe.dto.`in`.MenuCreateRequest
import kitchenpos.menus.tobe.dto.`in`.MenuProductCreateRequest
import kitchenpos.products.tobe.application.ProductService
import kitchenpos.products.tobe.domain.ProductRepository
import kitchenpos.products.tobe.dto.int.ProductCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private lateinit var menuGroupService: MenuGroupService

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var menuService: MenuService

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Test
    fun `상품 변경 시 해당 상품을 포함하는 메뉴 가격 정책에 맞지 않는 전시 중인 메뉴의 전시상태 INACTIVATE`() {
        //given
        val menuGroup = menuGroupService.createMenuGroup("주 메뉴")
        val product = productService.createProduct(
            ProductCreateRequest(
                displayedName = "양념치킨",
                price = BigDecimal.valueOf(20000).price()
            )
        )
        val menu = menuService.createMenu(
            MenuCreateRequest(
                name = "치킨",
                price = BigDecimal.valueOf(15000).price(),
                displayed = true,
                menuGroupId = menuGroup.id,
                menuProducts = listOf(
                    MenuProductCreateRequest(
                        productId = product.id,
                        quantity = 1
                    )
                )
            )
        )

        //when
        val `만원` = BigDecimal.valueOf(10000).price()
        productService.changePrice(product.id, `만원`)

        //then
        assertThat(productRepository.findById(product.id)?.price?.value?.compareTo(`만원`.value)).isEqualTo(0)
        assertThat(menuRepository.findByIdOrNull(menu.id)?.displayStatus).isEqualTo(false)
    }
}
