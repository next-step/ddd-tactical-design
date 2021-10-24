package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.application.tobe.OrderFixtures;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItems;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TobeOrderLineItemsTest extends OrderFixtures {

    @Test
    void 주문아이템들_생성() {
        TobeOrderLineItems orderLineItems = createOrderLineItems();

        assertThat(orderLineItems).isNotNull();
    }

    @Test
    void 주문아이템들_예외_확인() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TobeOrderLineItems(null));
    }

    @Test
    void 주문아이템들_금액_확인() {
        TobeOrderLineItems orderLineItems = createOrderLineItems();

        assertThat(orderLineItems.orderPrice()).isEqualTo(BigDecimal.valueOf(150L));
    }
}
