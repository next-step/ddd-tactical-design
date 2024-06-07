package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus
import kitchenpos.fixture.EatInOrderFixtures.waitingEatInOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EatInOrderTest {
    @Test
    fun `주문을 생성할 수 있다`() {
        val eatInOrder = waitingEatInOrder()

        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.WAITING)
    }
}
