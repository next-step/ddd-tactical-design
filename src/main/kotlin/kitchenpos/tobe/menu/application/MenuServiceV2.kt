package kitchenpos.tobe.menu.application

import kitchenpos.tobe.menu.application.dto.request.ChangeMenuPriceRequest
import kitchenpos.tobe.menu.application.dto.request.CreateMenuRequest
import kitchenpos.tobe.menu.application.dto.response.ChangeMenuPriceResponse
import kitchenpos.tobe.menu.application.dto.response.CreateMenuResponse
import kitchenpos.tobe.menu.application.dto.response.GetMenusResponse
import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.menu.domain.ProductClient
import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.menu.domain.repository.MenuGroupRepositoryV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import kitchenpos.tobe.menu.infra.getMenuById
import kitchenpos.tobe.menu.infra.getMenuGroupById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class MenuServiceV2(
    private val menuGroupRepository: MenuGroupRepositoryV2,
    private val productClient: ProductClient,
    private val menuPurgomalumClient: MenuPurgomalumClient,
    private val menuRepository: MenuRepositoryV2,
) {
    fun createMenu(request: CreateMenuRequest): CreateMenuResponse {
        val menuGroup = menuGroupRepository.getMenuGroupById(request.menuGroupId)
        val quantityPerProductIds = request.menuProducts.associate { it.productId to it.quantity }
        val productPrices = productClient.getProductPrices(quantityPerProductIds.keys.toList())
        val menuProducts =
            productPrices.map {
                MenuProductV2.from(
                    productId = it.id,
                    price = it.price,
                    quantity = quantityPerProductIds[it.id]!!,
                )
            }.toMutableList()

        val menu =
            MenuV2.from(
                name = request.name,
                price = request.price,
                displayed = request.displayed,
                menuGroup = menuGroup,
                menuProducts = menuProducts,
                menuPurgomalumClient = menuPurgomalumClient,
            )

        val saved = menuRepository.save(menu)
        return CreateMenuResponse.of(saved)
    }

    fun changeMenuPrice(
        menuId: UUID,
        request: ChangeMenuPriceRequest,
    ): ChangeMenuPriceResponse {
        val menu = menuRepository.getMenuById(menuId)
        menu.changeMenuPrice(price = request.price)
        return ChangeMenuPriceResponse.of(menu)
    }

    fun display(menuId: UUID): UUID {
        val menu = menuRepository.getMenuById(menuId)
        menu.display()
        return menu.id
    }

    fun hide(menuId: UUID): UUID {
        val menu = menuRepository.getMenuById(menuId)
        menu.hide()
        return menu.id
    }

    fun findAll(): GetMenusResponse {
        val menus = menuRepository.findAll()
        return GetMenusResponse.of(menus)
    }
}
