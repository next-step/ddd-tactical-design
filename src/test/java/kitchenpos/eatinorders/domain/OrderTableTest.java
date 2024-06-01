package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.OrderTable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 테이블 도메인 테스트")
public class OrderTableTest {

    @Test
    @DisplayName("주문 테이블을 등록한다.")
    void create() {
        OrderTable 주문_테이블 = EatInOrderFixture.emptyOrderTableOf("주문_테이블");

        Assertions.assertThat(주문_테이블.getId()).isNotNull();
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("주문 테이블의 이름은 null이거나 공백일 수 없다.")
    void checkValidatedName(String name) {
        Assertions.assertThatThrownBy(
                () -> EatInOrderFixture.emptyOrderTableOf(name)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 테이블이 등록될 때 방문한 손님의 수는 0으로 설정된다.")
    void create_numberOfGuests() {
        Assertions.assertThatThrownBy(
                () -> new OrderTable(UUID.randomUUID(), "주문_테이블", -1, true)
        ).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(
                () -> new OrderTable(UUID.randomUUID(), "주문_테이블", 1, true)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 테이블을 해지할 수 있다.")
    void sit() {
        OrderTable 빈_주문_테이블 = EatInOrderFixture.emptyOrderTableOf("주문_테이블");
        빈_주문_테이블.sit();

        Assertions.assertThat(빈_주문_테이블.isOccupied()).isTrue();
    }

    @Test
    @DisplayName("방문한 손님의 수를 변경할 수 있다.")
    void changeNumberOfGuests() {
        OrderTable 주문_테이블 = EatInOrderFixture.sitOrderTableOf("주문_테이블");
        int 변경_숫자 = 5;
        주문_테이블.changeNumberOfGuests(변경_숫자);

        Assertions.assertThat(주문_테이블.getNumberOfGuests()).isEqualTo(변경_숫자);
    }

    @Test
    @DisplayName("빈 테이블은 방문한 손님의 수를 변경할 수 없다.")
    void changeNumberOfGuests_exception_emptyTable() {
        OrderTable 빈_테이블 = EatInOrderFixture.emptyOrderTableOf("주문_테이블");

        Assertions.assertThatThrownBy(
                () -> 빈_테이블.changeNumberOfGuests(5)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("빈 테이블로 설정할 수 있다.")
    void clear() {
        OrderTable 주문_테이블 = EatInOrderFixture.sitOrderTableOf("주문_테이블");
        주문_테이블.changeNumberOfGuests(5);
        주문_테이블.clear();

        assertAll(
                () -> Assertions.assertThat(주문_테이블.getNumberOfGuests()).isZero(),
                () -> Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue()
        );
    }
}
