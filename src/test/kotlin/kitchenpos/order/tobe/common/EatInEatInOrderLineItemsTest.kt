package kitchenpos.order.tobe.common

import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItems
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class EatInEatInOrderLineItemsTest {

    @Test
    @DisplayName("OrderLineItems는 빈 목록이 될 수 없다.")
    fun emptyListFail() {
        assertThatIllegalArgumentException().isThrownBy {
            EatInOrderLineItems(emptyList())
        }
    }

}
