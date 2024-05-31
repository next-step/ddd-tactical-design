package tobe.domain

import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.menu.domain.entity.MenuV2
import tobe.domain.MenuGroupFixtures.createMenuGroup
import tobe.domain.MenuProductsFixtures.createMenuProduct

object MenuFixtures {
    fun createMenu(): MenuV2 {
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
            menuGroup = createMenuGroup(),
            menuProducts = mutableListOf(createMenuProduct()),
            menuPurgomalumClient = menuPurgomalumClient,
        )
    }
}
