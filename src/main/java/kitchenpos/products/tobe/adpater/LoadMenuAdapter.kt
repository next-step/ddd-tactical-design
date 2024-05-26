package kitchenpos.products.tobe.adpater

import kitchenpos.menus.domain.Menu
import kitchenpos.menus.domain.MenuRepository
import kitchenpos.products.tobe.port.LoadMenuPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class LoadMenuAdapter(
    private val menuRepository: MenuRepository,
) : LoadMenuPort {
    override fun findAllByProductId(productId: UUID): List<Menu> = menuRepository.findAllByProductId(productId)
}
