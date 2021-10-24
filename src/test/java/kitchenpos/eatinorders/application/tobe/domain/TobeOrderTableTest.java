package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.application.tobe.infra.FakeOrderAdaptor;
import kitchenpos.eatinorders.tobe.domain.TableName;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TobeOrderTableTest {
    private FakeOrderAdaptor orderAdaptor;

    @BeforeEach
    void setUp() {
        orderAdaptor = new FakeOrderAdaptor();
    }

    @Test
    void 주문_테이블_생성() {
        TobeOrderTable orderTable = createOrderTable();

        assertThat(orderTable).isNotNull();
    }

    @Test
    void 주문_테이블_세팅() {
        TobeOrderTable orderTable = createOrderTable();
        orderTable.setTheTable();
        assertThat(orderTable.isEmpty()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void 주문_테이블_인원_변경(int guests) {
        TobeOrderTable orderTable = createOrderTable();

        orderTable.setTheTable();
        orderTable.changeNumberOfGuests(guests);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(guests);
    }

    @Test
    void 주문_테이블_인원_예외() {
        TobeOrderTable orderTable = createOrderTable();

        assertThatIllegalStateException().isThrownBy(() -> orderTable.changeNumberOfGuests(5));
    }

    @Test
    void 주문_테이블_정리() {
        TobeOrderTable orderTable = createOrderTable();
        orderTable.clear(orderAdaptor);
        assertAll(
                () -> assertThat(orderTable.isEmpty()).isTrue(),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(0)
        );
    }

    private TobeOrderTable createOrderTable() {
        return new TobeOrderTable(createTableName("이름"));
    }

    private TableName createTableName(String name) {
        return new TableName(name);
    }
}
