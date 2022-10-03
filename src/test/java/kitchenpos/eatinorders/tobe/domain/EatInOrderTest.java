package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.UUID;
import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class EatInOrderTest {
    @DisplayName("매장 주문 생성 검증")
    @Test
    void create() {
        // given
        UUID id = UUID.randomUUID();
        EatInOrderStatus eatInOrderStatus = EatInOrderStatus.WAITING;
        LocalDateTime orderDateTime = LocalDateTime.now();
        OrderLineItems orderLineItems = new OrderLineItems(TobeFixtures.orderLineItem());
        String orderTableId = UUID.randomUUID().toString();

        // when
        EatInOrder eatInOrder = new EatInOrder(id, eatInOrderStatus, orderDateTime,
            orderLineItems, orderTableId);

        // then
        assertAll(
            () -> assertThat(eatInOrder.getId()).isNotNull(),
            () -> assertThat(eatInOrder.getStatus()).isEqualTo(eatInOrderStatus),
            () -> assertThat(eatInOrder.getOrderDateTime()).isEqualTo(orderDateTime),
            () -> assertThat(eatInOrder.getOrderLineItems()).isNotNull(),
            () -> assertThat(eatInOrder.getOrderTableId()).isNotNull()
        );
    }

    @DisplayName("접수 상태로 변경")
    @Test
    void accept() {
        // given
        EatInOrder eatInOrder = create(EatInOrderStatus.WAITING);

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 상태로 변경시 접수 대기 상태가 아닐 경우 예외 발생")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    void acceptFail(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = create(status);

        // when then
        assertThatIllegalStateException().isThrownBy(eatInOrder::accept);
    }

    @DisplayName("서빙 상태로 변경")
    @Test
    void serve() {
        // given
        EatInOrder eatInOrder = create(EatInOrderStatus.ACCEPTED);

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("서빙 상태로 변경시 접수 상태가 아닐 경우 예외 발생")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    void serveFail(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = create(status);

        // when then
        assertThatIllegalStateException().isThrownBy(eatInOrder::serve);
    }

    @DisplayName("계산 완료 상태로 변경")
    @Test
    void complete() {
        // given
        EatInOrder eatInOrder = create(EatInOrderStatus.SERVED);

        // when
        eatInOrder.complete();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    @DisplayName("계산 완료 상태로 변경시 서빙 상태가 아닐 경우 예외 발생")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    void completeFail(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = create(status);

        // when then
        assertThatIllegalStateException().isThrownBy(eatInOrder::complete);
    }

    private EatInOrder create(EatInOrderStatus status) {
        return new EatInOrder(UUID.randomUUID(), status, LocalDateTime.now(),
            new OrderLineItems(TobeFixtures.orderLineItem()), UUID.randomUUID().toString());
    }
}
