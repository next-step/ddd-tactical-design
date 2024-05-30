package tobe.domain

import kitchenpos.tobe.menu.domain.entity.MenuProductV2
import kitchenpos.tobe.menu.domain.entity.MenuV2
import tobe.domain.MenuGroupFixtures.createMenuGroup
import java.util.*

object MenuFixtures {
    fun createMenu(): MenuV2 {
        return MenuV2.of(
            name = "후라이드치킨",
            price = 16000.toBigDecimal(),
            displayed = true,
            menuGroup = createMenuGroup(),
            menuProducts =
                mutableListOf(
                    MenuProductV2.of(
                        price = 16000.toBigDecimal(),
                        quantity = 1,
                        productId = UUID.randomUUID(),
                    ),
                ),
        )
    }
}
