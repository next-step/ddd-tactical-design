package kitchenpos.menu.tobe.application

import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.application.dto.CreateMenuReq
import kitchenpos.menu.tobe.application.dto.MenuResp
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuName
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuPrice
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service(value = "tobeMenuService")
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val productRepository: ProductRepository,
    private val profanities: Profanities
) {
    @Transactional
    fun create(request: CreateMenuReq): MenuResp {
        val menuGroup = menuGroupRepository.findById(request.menuGroupId)
            .orElseThrow { NoSuchElementException("존재하지 않는 메뉴 그룹입니다.") }
        val products = productRepository.findAllByIdIn(request.menuProducts.map { it.productId })

        val menu = Menu(
            menuName = MenuName(menuNamePolicy = MenuNamePolicy(profanities), name = request.name),
            menuPrice = MenuPrice(request.price.toBigDecimal()),
            menuGroup = menuGroup,
            menuDisplay = request.display,
            menuProducts = MenuProducts(request.menuProducts.map { menuProductReq ->
                val product = products.find { it.id == menuProductReq.productId }
                    ?: throw NoSuchElementException("존재하지 않는 상품입니다.")
                MenuProduct(product = product, quantity = menuProductReq.quantity)
            })
        )
        menuRepository.save(menu)
        return MenuResp.of(menu)
    }

}
