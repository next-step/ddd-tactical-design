package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.*
import kitchenpos.fixture.EatInOrderFixtures.completedEatInOrder
import kitchenpos.fixture.EatInOrderFixtures.eatInOrderLineItem
import kitchenpos.fixture.EatInOrderFixtures.orderTable
import kitchenpos.fixture.EatInOrderFixtures.servedEatInOrder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class EatInOrderTableServiceTest {
    private val eatInOrderRepository: EatInOrderRepository = FakeEatInOrderRepository
    private val orderTableClearValidator: OrderTableClearValidator =
        DefaultOrderTableClearValidator(eatInOrderRepository)

    @Test
    fun `완료되지 않은 주문이 있을 경우 주문테이블을 비울 수 없다`() {
        //given
        val orderTable = orderTable()
        eatInOrderRepository.save(servedEatInOrder(orderTable.id, EatInOrderLineItems(listOf(eatInOrderLineItem()))))

        //when&then
        assertThatThrownBy { orderTable.clear(orderTableClearValidator) }
            .isInstanceOf(IllegalStateException::class.java)
            .withFailMessage("완료되지 않은 주문이 있는 경우 테이블을 비어있을 수 없습니다")
    }

    @Test
    fun `주문테이블을 비울 수 있다`() {
        //given
        val orderTable = orderTable()
        eatInOrderRepository.save(completedEatInOrder(orderTable.id, EatInOrderLineItems(listOf(eatInOrderLineItem()))))

        //when
        orderTable.clear(orderTableClearValidator)

        //then
        assertThat(orderTable.occupied).isFalse()
        assertThat(orderTable.numberOfGuests).isEqualTo(OrderTableGuestNumber.ZERO)
    }
}
