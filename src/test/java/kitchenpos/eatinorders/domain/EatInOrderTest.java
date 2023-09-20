package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.domain.EatInOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderStatus;

class EatInOrderTest {
    private EatInOrder eatIn;

    @BeforeEach
    void setUp() {
        eatIn = new EatInOrder(createOrderLineItems(), UUID.randomUUID());
    }

    @DisplayName("주문 테이블이 null 이면 주문을 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void name(UUID orderTableId) {
        assertThatThrownBy(() -> new EatInOrder(createOrderLineItems(), orderTableId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("테이블이 없으면 매장식사 주문을 할 수 없습니다.");
    }

    @DisplayName("주문을 수락 한다.")
    @Test
    void accept1() {
        eatIn.accept();
        assertThat(eatIn.isSameStatus(EatInOrderStatus.ACCEPTED)).isTrue();
    }

    @DisplayName("주문이 대기 상태가 아니면 수락을 할 수 없다.")
    @Test
    void accept2() {
        eatIn.accept();
        eatIn.serve();
        assertThatThrownBy(() -> eatIn.accept())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수 대기 중인 주문만 접수할 수 있다.");
    }

    @DisplayName("주문을 서빙 한다.")
    @Test
    void serve1() {
        eatIn.accept();
        eatIn.serve();
        assertThat(eatIn.isSameStatus(EatInOrderStatus.SERVED)).isTrue();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serve2() {
        assertThatThrownBy(() -> eatIn.serve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("접수된 주문만 서빙할 수 있다.");
    }

    @DisplayName("주문을 종료 한다.")
    @Test
    void complete1() {
        eatIn.accept();
        eatIn.serve();
        eatIn.complete();
        assertThat(eatIn.isSameStatus(EatInOrderStatus.COMPLETED)).isTrue();
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @Test
    void complete3() {
        eatIn.accept();
        assertThatThrownBy(() -> eatIn.complete())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("서빙된 주문만 완료할 수 있다.");
    }
}
