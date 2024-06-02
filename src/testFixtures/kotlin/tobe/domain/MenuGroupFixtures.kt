package tobe.domain

import kitchenpos.tobe.menu.domain.entity.MenuGroupV2

object MenuGroupFixtures {
    fun createMenuGroup(): MenuGroupV2 {
        return MenuGroupV2.of(
            name = "한마리 메뉴",
        )
    }
}
