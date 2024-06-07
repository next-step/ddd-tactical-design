package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

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

    @DisplayName("예외 테스트")
    @Nested
    class ExceptionTest {
        @Test
        @DisplayName("주문 항목 생성 시 금액이 null인 경우 예외가 발생한다.")
        void create_checkPrice() {
            Assertions.assertThatThrownBy(
                    () -> EatInOrderFixture.orderLineItemOf(1, null)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주문 항목 생성 시 금액이 음수일 경우 예외가 발생한다.")
        void create_checkPrice_negative() {
            Assertions.assertThatThrownBy(
                    () -> EatInOrderFixture.orderLineItemOf(1, BigDecimal.valueOf(-1))
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주문 항목 생성 시 메뉴 식별자가 존재하지 않을 경우 예외가 발생한다.")
        void create_checkMenuId() {
            Assertions.assertThatThrownBy(
                    () -> new OrderLineItem(new Random().nextLong(), 1, BigDecimal.ONE, null)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
