package kitchenpos.menus.tode.adapter

import kitchenpos.menus.domain.Menu
import kitchenpos.menus.domain.MenuRepository
import kitchenpos.menus.tode.port.MenuReader
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Component
class DefaultMenuReader(
    private val menuRepository: MenuRepository,
) : MenuReader {
    override fun findAllByProductId(productId: UUID): List<Menu> = menuRepository.findAllByProductId(productId)
}
