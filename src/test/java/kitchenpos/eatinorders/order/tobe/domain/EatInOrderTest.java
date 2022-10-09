package kitchenpos.eatinorders.order.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderTest {

    @DisplayName("매장주문을 생성한다.")
    @Test
    void create() {
        final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
        final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
        final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);

        final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);

        assertAll(
                () -> assertThat(eatInOrder.id()).isNotNull(),
                () -> assertThat(eatInOrder.orderTableId()).isNotNull(),
                () -> assertThat(eatInOrder.orderDateTime()).isNotNull(),
                () -> assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.eatInOrderLineItems()).isNotNull()
        );
    }

    @DisplayName("매장 주문을 수락한다.")
    @Nested
    class AcceptTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
            final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
            final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);
            final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);

            eatInOrder.accept();

            assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.ACCEPTED);
        }

        @ParameterizedTest(name = "상태가 대기상태일 때만 매장 주문을 수락할 수 있다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"WAITING"},
                mode = EnumSource.Mode.EXCLUDE)
        void error(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), UUID.randomUUID(), status);

            assertThatThrownBy(() -> eatInOrder.accept())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("주문 상태가 대기일 때만 가능합니다.");
        }
    }
}
