package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.entity.MenuGroupV2
import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.menu.domain.repository.MenuGroupRepositoryV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import java.util.UUID

fun MenuRepositoryV2.getMenuById(menuId: UUID): MenuV2 {
    return this.findMenuById(menuId)
        ?: throw IllegalArgumentException("[$menuId] 존재하지 않는 메뉴입니다.")
}

fun MenuGroupRepositoryV2.getMenuGroupById(menuGroupId: UUID): MenuGroupV2 {
    return this.findMenuGroupById(menuGroupId)
        ?: throw IllegalArgumentException("[$menuGroupId]존재하지 않는 메뉴 그룹입니다.")
}
