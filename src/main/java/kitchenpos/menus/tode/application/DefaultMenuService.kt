package kitchenpos.menus.tode.application

import kitchenpos.menus.application.MenuService
import kitchenpos.menus.domain.Menu
import kitchenpos.menus.domain.MenuGroupRepository
import kitchenpos.menus.domain.MenuProduct
import kitchenpos.menus.domain.MenuRepository
import kitchenpos.menus.tode.domain.MenuCreateFactory
import kitchenpos.menus.tode.domain.MenuNameValidator
import kitchenpos.menus.tode.domain.MenuPriceValidator
import kitchenpos.menus.tode.domain.MenuProductValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service("MenuService")
class DefaultMenuService(
    private val menuGroupRepository: MenuGroupRepository,
    private val menuProductSizeValidator: MenuProductSizeValidator,
    private val menuProductService: MenuProductService,
    private val menuNameValidator: MenuNameValidator,
    private val menuRepository: MenuRepository,
) : MenuService {
    override fun create(request: Menu): Menu {
        val menuPrice = request.price
        MenuPriceValidator.requireNormalPrice(menuPrice)

        val menuGroup = menuGroupRepository.findById(request.menuGroupId)
            .orElseThrow { throw NoSuchElementException() }

        val menuProductRequests: List<MenuProduct>? = request.menuProducts
        MenuProductValidator.requireNormalMenuProduct(menuProductRequests)

        menuProductSizeValidator.requireProductSizeMatch(
            menuProductRequests!!.map {
                it.productId
            }
        )

        val menuProducts = menuProductService.createMenuProduct(
            menuPrice = menuPrice,
            menuProductRequests = menuProductRequests
        )

        val menuName = request.name
        menuNameValidator.requireNormalName(menuName)

        val menu = MenuCreateFactory.createMenu(
            name = menuName,
            price = menuPrice,
            menuGroup = menuGroup,
            isDisplayed = request.isDisplayed,
            menuProducts = menuProducts
        )

        return menuRepository.save(menu)
    }

    override fun changePrice(
        menuId: UUID,
        request: Menu,
    ): Menu {
        TODO("Not yet implemented")
    }

    override fun display(menuId: UUID): Menu {
        TODO("Not yet implemented")
    }

    override fun hide(menuId: UUID): Menu {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Menu> {
        TODO("Not yet implemented")
    }
}