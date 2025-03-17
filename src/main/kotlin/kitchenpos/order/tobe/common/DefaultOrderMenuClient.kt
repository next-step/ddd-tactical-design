package kitchenpos.order.tobe.common

import java.util.*
import kitchenpos.menu.tobe.domain.MenuRepository
import org.springframework.stereotype.Service

@Service
class  DefaultOrderMenuClient(
    private val menuRepository: MenuRepository,
) : OrderMenuClient {
    override fun getMenu(menuId: UUID): OrderMenuInfo {
        return menuRepository.findById(menuId).orElseThrow { NoSuchElementException("존재하지않는 메뉴입니다.") }
            .let { menu ->
                OrderMenuInfo(
                    menuId = menu.id,
                    menuDisplay = menu.menuDisplay,
                )
            }
    }
}
