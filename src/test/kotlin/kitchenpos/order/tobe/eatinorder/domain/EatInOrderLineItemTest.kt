package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.order.tobe.common.OrderMenuInfo
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class EatInOrderLineItemTest {

    @Test
    @DisplayName("EatInOrderLineItem을 생성한다.")
    fun create() {
        val eatInOrderLineItem = EatInOrderLineItem.create(
            seq = 1,
            menuInfo = OrderMenuInfo(
                menuId = UUID.randomUUID(),
                menuDisplay = MenuDisplay.DISPLAYED,
            ),
            quantity = 1,
        )

        assertAll(
            { Assertions.assertThat(eatInOrderLineItem.seq).isEqualTo(1) },
            { Assertions.assertThat(eatInOrderLineItem.menuId).isNotNull() },
            { Assertions.assertThat(eatInOrderLineItem.quantity).isEqualTo(1) },
        )
    }

    @Test
    @DisplayName("Display Menu만 EatInOrderLineItem으로 생성할 수 있다.")
    fun createWithNotDisplayMenu() {
        assertThatIllegalStateException().isThrownBy {
            EatInOrderLineItem.create(
                seq = 1,
                menuInfo = OrderMenuInfo(
                    menuId = UUID.randomUUID(),
                    menuDisplay = MenuDisplay.NOT_DISPLAYED,
                ),
                quantity = 1,
            )
        }
    }
}
