package kitchenpos.tableorders.tobe.domain.model;

import kitchenpos.global.domain.vo.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {

    @DisplayName("주문테이블(order table) 은 반드시 이름, 방문 손님 수(number of guest), 지정 여부를 가진다.")
    @Test
    void create() {

        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);

        assertAll(
                () -> assertThat(table.getId()).isNotNull(),
                () -> assertThat(table.getName()).isEqualTo(테이블_이름),
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("주문테이블(order table)을 지정(assign)할 수 있다")
    @Test
    void assign() {
        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);

        table.assign();

        assertThat(table.isEmpty()).isFalse();
    }

    @DisplayName("주문테이블(order table)을 정리(clear)할 수 있다")
    @Test
    void clear() {

        //given
        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);
        table.assign();

        //when
        table.clear();

        assertAll(
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("주문테이블(order table)이 지정되어 있지 않으면 인원을 변경할 수 없다.")
    @Test
    void changeNumberOfGuest01() {
        //given
        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);


        //when & then
        assertThatThrownBy(() -> table.changNumberOfGuest(2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("먼저 테이블을 지정해야합니다.");
    }

    @DisplayName("주문테이블(order table)의 인원은 음수일 수 없다.")
    @Test
    void changeNumberOfGuest02() {
        //given
        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);
        table.assign();

        //when & then
        assertThatThrownBy(() -> table.changNumberOfGuest(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("테이블 인원은 음수일 수 없습니다.");
    }


    @DisplayName("주문테이블(order table)의 인원을 변경할 수 있다.")
    @Test
    void changeNumberOfGuest03() {
        //given
        Name 테이블_이름 = new Name("루프탑 테이블");
        OrderTable table = new OrderTable(테이블_이름);
        table.assign();

        //when
        table.changNumberOfGuest(10);

        //then
        assertThat(table.getNumberOfGuests()).isEqualTo(10);
    }
}
