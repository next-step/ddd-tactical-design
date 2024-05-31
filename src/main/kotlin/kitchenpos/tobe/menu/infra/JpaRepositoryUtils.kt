package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import java.util.UUID

fun MenuRepositoryV2.findMenuByIdOrThrow(menuId: UUID): MenuV2 {
    return this.findMenuById(menuId)
        ?: throw IllegalArgumentException("존재하지 않는 메뉴입니다.")
}
