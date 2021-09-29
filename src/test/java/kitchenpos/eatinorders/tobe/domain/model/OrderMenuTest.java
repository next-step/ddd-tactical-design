package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderMenuTest {

    @DisplayName("주문 메뉴를 생성한다.")
    @Test
    void 주문_메뉴_생성_성공() {
        final Price price = new Price(BigDecimal.valueOf(16_000L));

        final OrderMenu orderMenu = new OrderMenu(UUID.randomUUID(), price, true);

        assertAll(
                () -> assertThat(orderMenu.getId()).isNotNull(),
                () -> assertThat(orderMenu.getPrice()).isEqualTo(price.value()),
                () -> assertThat(orderMenu.isDisplayed()).isTrue()
        );
    }
}
