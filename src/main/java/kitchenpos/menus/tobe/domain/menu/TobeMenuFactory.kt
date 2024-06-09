package kitchenpos.menus.tobe.domain.menu

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.shared.domain.DefaultProfanities
import org.springframework.stereotype.Component
import java.util.*

@Component
class TobeMenuFactory(
    private val tobeMenuRepository: TobeMenuRepository,
    private val tobeMenuGroupRepository: TobeMenuGroupRepository,
) {
    fun createMenu(request: CreateMenuRequest): TobeMenu {
        val name = request.name
        val price = request.price
        val groupId = request.groupId
        val displayed = request.displayed
        val menuProducts = request.convert()
        val profanities = DefaultProfanities()

        validateExistsMenuGroup(groupId)

        return TobeMenu(name, price, displayed, groupId, menuProducts, profanities)
    }

    private fun validateExistsMenuGroup(groupId: UUID) {
        if (!tobeMenuGroupRepository.existsById(groupId)) {
            throw NoSuchElementException()
        }
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
}
