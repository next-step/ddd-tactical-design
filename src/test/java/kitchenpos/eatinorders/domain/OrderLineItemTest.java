package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 항목 도메인 테스트")
public class OrderLineItemTest {

    @Test
    @DisplayName("주문 항목을 생성한다.")
    void create() {
        OrderLineItem 주문_항목 = EatInOrderFixture.orderLineItemOf(1, BigDecimal.ONE);

        assertAll(
                () -> Assertions.assertThat(주문_항목.getSeq()).isNotNull(),
                () -> Assertions.assertThat(주문_항목.getQuantity()).isNotNull(),
                () -> Assertions.assertThat(주문_항목.getPrice()).isNotNull()
        );
    }
}
