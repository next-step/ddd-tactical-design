package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EatInOrderTest {

    @DisplayName("주문 항목이 없거나 비어있으면 매장 주문을 생성할 수 없다.")
    @ParameterizedTest(name = "주문 항목: {0}")
    @NullAndEmptySource
    void createWithoutEatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
