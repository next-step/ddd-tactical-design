package kitchenpos.menu.tobe.application

import java.util.*
import kitchenpos.menu.tobe.application.dto.CreateMenuReq
import kitchenpos.menu.tobe.application.dto.MenuResp
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuName
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuPrice
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service(value = "tobeMenuService")
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val productRepository: ProductRepository,
    private val menuNamePolicy: MenuNamePolicy,
    private val menuAmountService: MenuAmountService,
) {
    @Transactional
    fun create(request: CreateMenuReq): MenuResp {
        val menuGroup = menuGroupRepository.findById(request.menuGroupId)
            .orElseThrow { NoSuchElementException("존재하지 않는 메뉴 그룹입니다.") }
        val products = productRepository.findAllByIdIn(request.menuProducts.map { it.productId })

        val menu = Menu(
            menuAmountService = menuAmountService,
            menuName = MenuName(menuNamePolicy = menuNamePolicy, name = request.name),
            menuPrice = MenuPrice(request.price.toBigDecimal()),
            menuGroup = menuGroup,
            menuDisplay = request.display,
            menuProducts = MenuProducts(request.menuProducts.map { menuProductReq ->
                val product = products.find { it.id == menuProductReq.productId }
                    ?: throw NoSuchElementException("존재하지 않는 상품입니다.")
                MenuProduct(productId = product.id, quantity = menuProductReq.quantity)
            })
        )
        menuRepository.save(menu)
        return MenuResp.of(menu)
    }

    @Transactional
    fun changePrice(menuId: UUID, request: ChangeProductPriceReq) {
        val menu = menuRepository.findById(menuId)
            .orElseThrow { NoSuchElementException("존재하지 않는 메뉴입니다.") }
        menu.changePrice(menuAmountService = menuAmountService, MenuPrice(request.price))
        menuRepository.save(menu)
    }

    @Transactional
    fun display(menuId: UUID) {
        val menu = menuRepository.findById(menuId)
            .orElseThrow { NoSuchElementException("존재하지 않는 메뉴입니다.") }
        menu.display(menuAmountService)
    }

    @Transactional
    fun notDisplay(menuId: UUID) {
        val menu = menuRepository.findById(menuId)
            .orElseThrow { NoSuchElementException("존재하지 않는 메뉴입니다.") }
        menu.notDisplay()
    }
}
