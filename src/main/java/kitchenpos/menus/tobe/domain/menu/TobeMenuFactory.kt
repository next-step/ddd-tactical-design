package kitchenpos.menus.tobe.domain.menu

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository
import kitchenpos.shared.domain.DefaultProfanities
import org.springframework.stereotype.Component
import java.util.*

@Component
class TobeMenuFactory(
    private val tobeMenuGroupRepository: TobeMenuGroupRepository,
    private val tobeProductClient: TobeProductClient,
) {
    fun createMenu(request: CreateMenuRequest): TobeMenu {
        val name = request.name
        val price = request.price
        val groupId = request.groupId
        val displayed = request.displayed
        val menuProducts = request.convert()
        val profanities = DefaultProfanities()

        validateExistsMenuGroup(groupId)
        validateExistsProduct(menuProducts)

        return TobeMenu(name, price, displayed, groupId, menuProducts, profanities)
    }

    private fun CreateMenuRequest.convert(): List<TobeMenuProduct> {
        return this.menuProducts.map {
            TobeMenuProduct(
                it.quantity,
                it.price,
                it.productId,
            )
        }
    }

    private fun validateExistsMenuGroup(groupId: UUID) {
        if (!tobeMenuGroupRepository.existsById(groupId)) {
            throw NoSuchElementException()
        }
    }

    private fun validateExistsProduct(menuProducts: List<TobeMenuProduct>) {
        val menuProductIds = menuProducts.map { it.productId }
        val allProductsExists = tobeProductClient.validateAllProductsExists(menuProductIds)

        if (!allProductsExists) {
            throw IllegalArgumentException("등록된 상품으로 메뉴를 등록할 수 있다.")
        }
    }
}
