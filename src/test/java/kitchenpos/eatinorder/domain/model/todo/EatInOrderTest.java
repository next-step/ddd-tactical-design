package kitchenpos.eatinorder.domain.model.todo;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {
    @DisplayName("EeaInOrder를 생성한다.")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final LocalDateTime orderDateTime = LocalDateTime.now();
        final UUID orderTableId = UUID.randomUUID();
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.of(1L, UUID.randomUUID(), 1L, 1L, true);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(eatInOrderLineItem);

        // when
        final EatInOrder eatInOrder = EatInOrder.create(id, orderDateTime, eatInOrderLineItems, orderTableId);

        // then
        assertAll(
                () -> assertThat(eatInOrder).isNotNull(),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTableId),
                () -> assertThat(eatInOrder.getOrderDateTime()).isEqualTo(orderDateTime),
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.getLineItems()).containsExactly(eatInOrderLineItem)
        );
    }

    @DisplayName("EeaInOrder를 수락한다.")
    @Test
    void accept() {
        // given
        final EatInOrder eatInOrder = createEatInOrder();

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("주문 상태가 '대기' 상태인 경우에만 수락할 수 있다.")
    @ValueSource(strings = {"ACCEPTED", "SERVED", "COMPLETED"})
    @ParameterizedTest
    void acceptWhenWaiting(EatInOrderStatus status) {
        // given
        final EatInOrder eatInOrder = createEatInOrder(status);

        // when
        ThrowableAssert.ThrowingCallable accept = eatInOrder::accept;

        // then
        assertThatThrownBy(accept)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("EeaInOrder를 제공한다.")
    @Test
    void serve() {
        // given
        final EatInOrder eatInOrder = createEatInOrder(EatInOrderStatus.ACCEPTED);

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("주문 상태가 '수락' 상태인 경우에만 제공할 수 있다.")
    @ValueSource(strings = {"WAITING", "SERVED", "COMPLETED"})
    @ParameterizedTest
    void serveWhenAccepted(EatInOrderStatus status) {
        // given
        final EatInOrder eatInOrder = createEatInOrder(status);

        // when
        ThrowableAssert.ThrowingCallable serve = eatInOrder::serve;

        // then
        assertThatThrownBy(serve)
                .isInstanceOf(IllegalStateException.class);
    }

    private static EatInOrder createEatInOrder() {
        return createEatInOrder(EatInOrderStatus.WAITING);
    }

    private static EatInOrder createEatInOrder(EatInOrderStatus status) {
        final UUID id = UUID.randomUUID();
        final LocalDateTime orderDateTime = LocalDateTime.now();
        final UUID orderTableId = UUID.randomUUID();
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.of(1L, UUID.randomUUID(), 1L, 1L, true);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(eatInOrderLineItem);
        return EatInOrder.create(id, orderDateTime, eatInOrderLineItems, orderTableId, status);
    }
}