package kitchenpos.orders.store.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("OrderTable")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTableTest {

    @Test
    void 주문테이블을_생성할_수_있다() {
        assertThatNoException()
                .isThrownBy(() -> new OrderTable("1번테이블"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 이름이_비어있거나_없는_주문테이블을_생성하면_예외를_던진다(String name) {
        assertThatThrownBy(() -> new OrderTable(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블에_손님이_앉으면_점유된다() {
        OrderTable target = new OrderTable("1번테이블");

        target.sit();

        assertThat(target.isOccupied()).isTrue();
    }

    @Test
    void 주문테이블을_치우면_손님은0명_비점유상태로_변경된다() {
        OrderTable target = new OrderTable("1번테이블");
        target.sit();
        target.changeNumberOfGuests(new NumberOfGuests(5));

        target.clear();

        assertAll(
                () -> assertThat(target.isOccupied()).isFalse(),
                () -> assertThat(target.hasGuest()).isFalse()
        );
    }
}