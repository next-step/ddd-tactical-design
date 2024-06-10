package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus
import kitchenpos.fixture.EatInOrderFixtures.acceptedEatInOrder
import kitchenpos.fixture.EatInOrderFixtures.completedEatInOrder
import kitchenpos.fixture.EatInOrderFixtures.servedEatInOrder
import kitchenpos.fixture.EatInOrderFixtures.waitingEatInOrder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class EatInOrderTest {
    @Test
    fun `주문을 생성할 수 있다`() {
        val eatInOrder = waitingEatInOrder()

        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.WAITING)
    }

    @Test
    fun `WAITING 상태가 아닐 경우 accept 불가능`() {
        val completedEatInOrder = completedEatInOrder()

        assertThatThrownBy {
            completedEatInOrder.accept()
        }.isInstanceOf(IllegalStateException::class.java).withFailMessage("접수대기 상태가 아닌 주문은 접수시킬수 없습니다")
    }

    @Test
    fun `WAITING 상태에서 accept시 ACCEPTED`() {
        val waitingEatInOrder = waitingEatInOrder()

        waitingEatInOrder.accept()

        assertThat(waitingEatInOrder.status).isEqualTo(EatInOrderStatus.ACCEPTED)
    }

    @Test
    fun `ACCEPTED 상태가 아닐 경우 serve 불가능`() {
        val waitingEatInOrder = waitingEatInOrder()

        assertThatThrownBy {
            waitingEatInOrder.serve()
        }.isInstanceOf(IllegalStateException::class.java).withFailMessage("접수 상태가 아닌 주문은 전달시킬수 없습니다")
    }

    @Test
    fun `ACCEPTED 상태에서 serve시 SERVED`() {
        val acceptedEatInOrder = acceptedEatInOrder()

        acceptedEatInOrder.serve()

        assertThat(acceptedEatInOrder.status).isEqualTo(EatInOrderStatus.SERVED)
    }

    @Test
    fun `SERVED 상태가 아닐 경우 complete 불가능`() {
        val waitingEatInOrder = waitingEatInOrder()

        assertThatThrownBy {
            waitingEatInOrder.complete()
        }.isInstanceOf(IllegalStateException::class.java).withFailMessage("전달 상태가 아닌 주문은 종료시킬수 없습니다")
    }

    @Test
    fun `SERVED 상태에서 complete시 COMPLETED`() {
        val servedEatInOrder = servedEatInOrder()

        servedEatInOrder.complete()

        assertThat(servedEatInOrder.status).isEqualTo(EatInOrderStatus.COMPLETED)
    }

}
