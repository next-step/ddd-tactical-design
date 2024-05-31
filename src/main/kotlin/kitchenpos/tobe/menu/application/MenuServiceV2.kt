package kitchenpos.tobe.menu.application

import kitchenpos.tobe.menu.application.dto.request.ChangeMenuPriceRequest
import kitchenpos.tobe.menu.application.dto.request.CreateMenuRequest
import kitchenpos.tobe.menu.application.dto.response.ChangeMenuPriceResponse
import kitchenpos.tobe.menu.application.dto.response.CreateMenuResponse
import kitchenpos.tobe.menu.domain.MenuDisplayChecker
import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.menu.domain.repository.MenuGroupRepositoryV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import kitchenpos.tobe.menu.infra.findMenuByIdOrThrow
import kitchenpos.tobe.product.domain.event.ProductPriceChangedEvent
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class MenuServiceV2(
    private val menuDisplayChecker: MenuDisplayChecker,
    private val menuGroupRepository: MenuGroupRepositoryV2,
    private val productRepository: ProductRepositoryV2,
    private val menuPurgomalumClient: MenuPurgomalumClient,
    private val menuRepository: MenuRepositoryV2,
) {
    fun createMenu(request: CreateMenuRequest): CreateMenuResponse {
        val menuGroup =
            menuGroupRepository.findMenuGroupById(request.menuGroupId)
                ?: throw IllegalArgumentException("존재하지 않는 메뉴 그룹입니다.")
        val menuProducts =
            request.menuProducts.map { menuProduct ->
                val product =
                    productRepository.findProductById(menuProduct.productId)
                        ?: throw IllegalArgumentException("존재하지 않는 상품입니다.")

                MenuProductV2.of(
                    productId = product.id,
                    price = product.getPrice(),
                    quantity = menuProduct.quantity,
                )
            }.toMutableList()
        val menu =
            MenuV2.of(
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
        val menu = menuRepository.findMenuByIdOrThrow(menuId)
        menu.changeMenuPrice(price = request.price)
        return ChangeMenuPriceResponse.of(menu)
    }

    fun display(menuId: UUID): UUID {
        val menu = menuRepository.findMenuByIdOrThrow(menuId)
        menu.display()
        return menu.id
    }

    fun unDisplay(menuId: UUID): UUID {
        val menu = menuRepository.findMenuByIdOrThrow(menuId)
        menu.unDisplay()
        return menu.id
    }

    fun findAll(menuId: UUID) {
        val menu = menuRepository.findAll()
    }

    fun handleProductPriceChanged(event: ProductPriceChangedEvent) {
        menuDisplayChecker.handleProductPriceChanged(event.productId, event.price)
    }
}
