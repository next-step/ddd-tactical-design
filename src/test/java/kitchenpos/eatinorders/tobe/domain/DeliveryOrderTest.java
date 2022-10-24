package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture.createDeliveryOrder;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
- [x] 1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.
- 배달 주문을 접수되면 배달 대행사를 호출한다.
- 주문을 배달한다.
- 배달 주문만 배달할 수 있다.
- 서빙된 주문만 배달할 수 있다.
- 주문을 배달 완료한다.
- 배달 중인 주문만 배달 완료할 수 있다.
- 배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.
- 배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.
  - 배달 주소는 비워 둘 수 없다.
- 배달 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.
 */
class DeliveryOrderTest {
    @DisplayName("1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.")
    @Test
    void register() {
        assertThatCode(() -> createDeliveryOrder())
                .doesNotThrowAnyException();
    }
}
