package kitchenpos.menu.tobe.infra

import java.util.*
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuProductPriceChanged
import kitchenpos.menu.tobe.domain.MenuRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class DefaultMenuProductPriceChanged(
    private val menuRepository: MenuRepository,
    private val amountService: MenuAmountService,
) : MenuProductPriceChanged {

    @Transactional
    override fun changedProduct(productId: UUID) {
        val menus = menuRepository.findAllByProductId(productId)
        menus.forEach {
            if (!it.canDisplay(amountService)) {
                it.notDisplay()
            }
        }

    }

}
