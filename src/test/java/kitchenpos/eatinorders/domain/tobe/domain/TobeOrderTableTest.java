package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.tobe.domain.vo.Guests;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableName;
import kitchenpos.eatinorders.domain.tobe.domain.vo.TableEmptyStatus;
import kitchenpos.support.infra.FakePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeOrderTableTest {

    @DisplayName(value = "주문테이블을 생성한다")
    @Test
    void create() throws Exception {
        //given
        OrderTableName name = new OrderTableName("1번", new FakePurgomalumClient());
        //when
        TobeOrderTable table = TobeOrderTable.Of(name);
        //then
        assertThat(table).isNotNull();
        assertAll(
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isZero(),
                () -> assertThat(table.getName().getValue()).isEqualTo("1번")
        );
    }

    @DisplayName(value = "테이블을 생성하기 위해선 반드시 이름이 있어야 한다")
    @NullSource
    @ParameterizedTest
    void create_fail(final OrderTableName name) throws Exception {
        //given&&when&&then
        assertThatThrownBy(() -> TobeOrderTable.Of(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName(value = "테이블의 상태를 빈 태이블로 변경할 수 있다")
    @Test
    void clear() throws Exception {
        //given
        OrderTableName name = new OrderTableName("1번", new FakePurgomalumClient());
        TobeOrderTable table = TobeOrderTable.Of(name, new Guests(1), TableEmptyStatus.OCCUPIED);
        //when
        table.clear();
        //then
        assertAll(
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName(value = "테이블의 상태를 착석 상태로 변경할 수 있다")
    @Test
    void sit() throws Exception {
        //given
        OrderTableName name = new OrderTableName("1번", new FakePurgomalumClient());
        TobeOrderTable table = TobeOrderTable.Of(name);
        //when
        table.sit();
        //then
        assertAll(
                () -> assertThat(table.isEmpty()).isFalse()
        );
    }

    @DisplayName(value = "테이블의 착석인원을 변경할 수 있다")
    @Test
    void changeNumberOfGuests() throws Exception {
        //given
        OrderTableName name = new OrderTableName("1번", new FakePurgomalumClient());
        TobeOrderTable table = TobeOrderTable.Of(name, new Guests(1), TableEmptyStatus.OCCUPIED);
        //when
        table.changeNumberOfGuests(new Guests(3));
        //then
        assertAll(
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(3)
        );
    }

    @DisplayName(value = "비어있는 테이블의 착석인원을 변경할 수 있다")
    @Test
    void changeNumberOfGuests_fail() throws Exception {
        //given
        OrderTableName name = new OrderTableName("1번", new FakePurgomalumClient());
        TobeOrderTable table = TobeOrderTable.Of(name);
        //when&&then
        assertThatThrownBy(() -> table.changeNumberOfGuests(new Guests(3)))
                .isInstanceOf(IllegalStateException.class);
    }
}
