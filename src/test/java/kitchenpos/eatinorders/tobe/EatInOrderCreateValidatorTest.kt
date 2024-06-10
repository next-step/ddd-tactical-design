package kitchenpos.eatinorders.tobe

import kitchenpos.common.price
import kitchenpos.eatinorders.tobe.domain.DefaultEatInOrderCreateValidator
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItems
import kitchenpos.eatinorders.tobe.domain.OrderTableGuestNumber
import kitchenpos.fixture.EatInOrderFixtures.eatInOrderLineItem
import kitchenpos.fixture.EatInOrderFixtures.orderTable
import kitchenpos.fixture.MenuFixtures.menu
import kitchenpos.menus.tobe.FakeMenuRepository
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class EatInOrderCreateValidatorTest {
    private val menuRepository = FakeMenuRepository()
    private val orderTableRepository = FakeOrderTableRepository
    private val eatInOrderCreateValidator = DefaultEatInOrderCreateValidator(menuRepository, orderTableRepository)

    @Test
    fun `빈 테이블에는 매장주문 생성 불가능`() {
        //given
        val orderTable = orderTable()
        orderTable.clear(AlwaysSuccessOrderTableClearValidator)
        orderTableRepository.save(orderTable)

        val menu = menu(displayStatus = true)
        menuRepository.save(menu)

        //when & then
        assertThatThrownBy {
            eatInOrderCreateValidator.validate(
                orderTable.id,
                EatInOrderLineItems(listOf(eatInOrderLineItem(menuId = menu.id, price = menu.price)))
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("빈 테이블에는 주문을 생성할 수 없습니다: ${orderTable.id}")
    }

    @Test
    fun `주문 항목의 가격과 메뉴의 가격이 다를 경우 주문 생성 불가능`() {
        //given
        val orderTable = orderTable()
        orderTable.sit()
        orderTable.changeNumberOfGuest(OrderTableGuestNumber(3))
        orderTableRepository.save(orderTable)

        val menu = menu(displayStatus = true)
        menuRepository.save(menu)

        //when & then
        val invalidPrice = BigDecimal.valueOf(9999).price()

        assertThatThrownBy {
            eatInOrderCreateValidator.validate(
                orderTable.id,
                EatInOrderLineItems(
                    listOf(
                        eatInOrderLineItem(
                            menuId = menu.id,
                            price = invalidPrice
                        )
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("주문항목의 가격과 메뉴의 가격은 동일해야합니다: ${menu.id}")
    }

    @Test
    fun `주문 항목의 메뉴의 전시상태가 비활성 상태인 경우 주문 생성 불가능`() {
        //given
        val orderTable = orderTable()
        orderTable.sit()
        orderTable.changeNumberOfGuest(OrderTableGuestNumber(3))
        orderTableRepository.save(orderTable)

        val menu = menu(displayStatus = false)
        menuRepository.save(menu)

        //when & then
        assertThatThrownBy {
            eatInOrderCreateValidator.validate(
                orderTable.id,
                EatInOrderLineItems(
                    listOf(
                        eatInOrderLineItem(
                            menuId = menu.id,
                            price = menu.price
                        )
                    )
                )
            )
        }.isInstanceOf(IllegalStateException::class.java).withFailMessage("전시 비활성 메뉴로 주문을 생성할 수 없습니다: ${menu.id}")
    }
}
