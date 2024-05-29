package kitchenpos.tobe.eatinorder.infra

import kitchenpos.tobe.eatinorder.domain.MenuClient
import kitchenpos.tobe.eatinorder.domain.dto.MenuData
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import org.springframework.stereotype.Service
import java.util.*

@Service
class MenuClientImpl(
    private val menuRepository: MenuRepositoryV2,
) : MenuClient {
    override fun getMenus(menuIds: List<UUID>): List<MenuData> {
        val menus = menuRepository.findAllByIdIn(menuIds)

        if (menus.size != menuIds.size) {
            throw IllegalArgumentException("메뉴가 존재하지 않습니다.")
        }

        return menus.map {
            MenuData(
                id = it.id,
                displayed = it.displayed,
                price = it.getPrice(),
            )
        }
    }
}
