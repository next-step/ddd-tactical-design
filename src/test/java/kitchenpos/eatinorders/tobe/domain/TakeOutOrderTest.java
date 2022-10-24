package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture.createTakeOutOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
- [x] 포장 주문의 경우 서빙된 주문만 완료할 수 있다.
- [x] 1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.
- [x] 포장 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.
 */
public class TakeOutOrderTest {
    @DisplayName("포장 주문 등록")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.",
            "포장 주문의 경우 주문 항목의 수량은 0 이상이어야 한다."
    })
    void registerWithTakeOut(String message) {
        assertThatCode(() -> createTakeOutOrder())
                .doesNotThrowAnyException();
    }

    @DisplayName("포장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @Test
    void completeTable() {
        final Order takeOutOrder = createTakeOutOrder();
        takeOutOrder.accept();
        takeOutOrder.serve();

        takeOutOrder.complete();

        assertThat(takeOutOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}
