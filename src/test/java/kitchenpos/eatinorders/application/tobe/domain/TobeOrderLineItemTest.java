package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.application.tobe.OrderFixtures;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class TobeOrderLineItemTest extends OrderFixtures {

    @Test
    void 주문아이템_생성() {
        TobeOrderLineItem orderLineItem = createOrderLineItem(BigDecimal.ZERO);

        assertThat(orderLineItem).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"0:0", "100:500", "1000:5000"}, delimiter = ':')
    void 주문아이템_금액_확인(BigDecimal price, BigDecimal totalPrice) {
        TobeOrderLineItem orderLineItem = createOrderLineItem(price);

        assertThat(orderLineItem.getPrice()).isEqualTo(totalPrice);
    }

    private TobeOrderLineItem createOrderLineItem(BigDecimal price) {
        return new TobeOrderLineItem(1L, createMenu(price), 5);
    }
}
