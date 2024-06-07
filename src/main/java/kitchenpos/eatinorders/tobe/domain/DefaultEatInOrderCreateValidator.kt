package kitchenpos.eatinorders.tobe.domain

import kitchenpos.menus.tobe.domain.MenuRepository
import org.springframework.stereotype.Component
import java.util.*
import kotlin.NoSuchElementException

@Component
class DefaultEatInOrderCreateValidator(
    private val menuRepository: MenuRepository,
    private val orderTableRepository: OrderTableRepository
) : EatInOrderCreateValidator {
    override fun validate(orderTableId: UUID, eatInOrderLineItems: EatInOrderLineItems) {
        validateOrderTableOccupied(orderTableId)
        validateOrderLineItems(eatInOrderLineItems)
    }

    private fun validateOrderTableOccupied(orderTableId: UUID) {
        val orderTable = orderTableRepository.findByIdOrNull(orderTableId)
            ?: throw NoSuchElementException("can not found order table: $orderTableId")

        if (!orderTable.occupied) {
            throw IllegalArgumentException("빈 테이블에는 주문을 생성할 수 없습니다: $orderTableId")
        }
    }

    private fun validateOrderLineItems(eatInOrderLineItems: EatInOrderLineItems) {
        val menuMap = menuRepository.findByIdIn(eatInOrderLineItems.menuIds).associateBy { it.id }

        eatInOrderLineItems.items.forEach {
            val menu = menuMap[it.menuId] ?: throw IllegalArgumentException("존재하지 않는 메뉴로 주문을 생성할 수 없습니다: ${it.menuId}")

            if (!menu.displayStatus) {
                throw IllegalStateException("전시 비활성 메뉴로 주문을 생성할 수 없습니다: ${menu.id}")
            }

            if (menu.price != it.price) {
                throw IllegalArgumentException("주문항목의 가격과 메뉴의 가격은 동일해야합니다: ${menu.id}")
            }
        }
    }
}
