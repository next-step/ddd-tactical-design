package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.ordertable.tobe.domain.vo.GuestOfNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {

    @DisplayName("주문 테이블을 생성하면 빈 테이블이 생성된다.")
    @Test
    void create() {
        final OrderTable emptyTable = OrderTable.createEmptyTable("1번 테이블");

        assertAll(
                () -> assertThat(emptyTable.id()).isNotNull(),
                () -> assertThat(emptyTable.name()).isEqualTo(Name.valueOf("1번 테이블")),
                () -> assertThat(emptyTable.guestOfNumbers()).isEqualTo(GuestOfNumbers.ZERO),
                () -> assertThat(emptyTable.isEmptyTable()).isTrue()
        );
    }
}
