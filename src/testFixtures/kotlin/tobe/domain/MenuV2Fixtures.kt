package tobe.domain

import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.menu.domain.entity.MenuGroupV2
import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.product.domain.entity.ProductV2
import tobe.domain.MenuGroupV2Fixtures.createMenuGroup
import tobe.domain.MenuProductV2Fixtures.createMenuProduct

object MenuV2Fixtures {
    fun createMenu(
        menuGroup: MenuGroupV2? = null,
        product: ProductV2? = null,
    ): MenuV2 {
        val menuPurgomalumClient =
            object : MenuPurgomalumClient {
                override fun containsProfanity(text: String): Boolean {
                    return false
                }
            }

        return MenuV2.of(
            name = "후라이드치킨",
            price = 16000.toBigDecimal(),
            displayed = true,
            menuGroup = menuGroup ?: createMenuGroup(),
            menuProducts = mutableListOf(createMenuProduct(product)),
            menuPurgomalumClient = menuPurgomalumClient,
        )
    }
}
