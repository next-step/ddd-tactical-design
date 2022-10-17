package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderStatusTest {

    @ParameterizedTest(name = "대기 상태의 주문만 수락할 수 있다. source = {0}")
    @EnumSource(value = EatInOrderStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "WAITING")
    void accept(EatInOrderStatus status) {
        assertThatThrownBy(status::accept)
                .isInstanceOf(IllegalOrderStatusException.class);
    }

    @ParameterizedTest(name = "수락 상태의 주문만 서빙할 수 있다. source = {0}")
    @EnumSource(value = EatInOrderStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "ACCEPTED")
    void serve(EatInOrderStatus status) {
        assertThatThrownBy(status::serve)
                .isInstanceOf(IllegalOrderStatusException.class);
    }

    @ParameterizedTest(name = "서빙 상태의 주문만 완할 수 있다. source = {0}")
    @EnumSource(value = EatInOrderStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "SERVED")
    void complete(EatInOrderStatus status) {
        assertThatThrownBy(status::complete)
                .isInstanceOf(IllegalOrderStatusException.class);
    }

    @DisplayName("주문 상태 대기>수락>서빙>완료")
    @Test
    void accept_serve_complete() {
        assertThatNoException()
                .isThrownBy(() -> EatInOrderStatus.WAITING.accept().serve().complete());
    }
}
