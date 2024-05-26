package kitchenpos.menus.tode.application

import kitchenpos.menus.application.MenuService
import kitchenpos.menus.domain.Menu
import kitchenpos.menus.tode.domain.MenuPriceValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service("MenuService")
class DefaultMenuService: MenuService {
    override fun create(request: Menu): Menu {
        MenuPriceValidator.requireNormalPrice(request.price)
        TODO("Not yet implemented")
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