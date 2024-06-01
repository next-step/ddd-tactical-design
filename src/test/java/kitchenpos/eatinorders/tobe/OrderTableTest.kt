package kitchenpos.eatinorders.tobe

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import kitchenpos.eatinorders.tobe.domain.OrderTable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

class OrderTableTest {
    @DisplayName("주문 테이블을 생성 가능하고, 초기값이 세팅되어야 한다.")
    @Test
    fun test1() {
        val orderTable = OrderTable(name = "상품")

        assertSoftly {
            orderTable.numberOfGuests shouldBe 0
            orderTable.occupied shouldBe false
            orderTable.name shouldBe "상품"
        }
    }

    @DisplayName("주문 테이블 생성 시, 이름이 비어있다면 IllegalArgumentException 예외 처리 한다.")
    @Test
    fun test2() {
        shouldThrowExactly<IllegalArgumentException> {
            createOrderTableFixture(name = "")
        }
    }

    @DisplayName("주문 테이블에 앉으면, 테이블 점유 상태가 된다.")
    @Test
    fun test3() {
        val orderTable = createOrderTableFixture()

        orderTable.sit()

        orderTable.occupied shouldBe true
    }

    @DisplayName("주문 테이블을 치울 수 있다.")
    @Test
    fun test4() {
        val orderTable = createOrderTableFixture()

        orderTable.clear()

        orderTable.numberOfGuests shouldBe 0
        orderTable.occupied shouldBe false
    }

    @DisplayName("주문 테이블의 손님 수를 변경할 수 있다.")
    @Test
    fun test5() {
        val orderTable = createOrderTableFixture()

        orderTable.changeNumberOfGuests(5)

        orderTable.numberOfGuests shouldBe 5
    }

    @DisplayName("주문 테이블의 손님 수는 음수로 변경할 수 없고, IllegalArgumentException 예외 처리를 한다.")
    @Test
    fun test6() {
        val orderTable = createOrderTableFixture()

        shouldThrowExactly<IllegalArgumentException> {
            orderTable.changeNumberOfGuests(-1)
        }
    }

    @DisplayName("점유 중인 상태의 테이블만 손님 수 변경이 가능하다.")
    @Test
    fun test7() {
        val orderTable = createOrderTableFixture(occupied = false)

        shouldThrowExactly<IllegalStateException> {
            orderTable.changeNumberOfGuests(1)
        }
    }

    private fun createOrderTableFixture(
        name: String = "1번 테이블",
        numberOfGuests: Int = 4,
        occupied: Boolean = true,
    ) = OrderTable(name).apply {
        this.numberOfGuests = numberOfGuests
        this.occupied = occupied
    }
}
