package kitchenpos.tobe.menu.domain.repository

import kitchenpos.tobe.menu.domain.entity.MenuV2
import java.util.*

interface MenuRepositoryV2 {
    fun findAllByProductId(productId: UUID): List<MenuV2>
}
