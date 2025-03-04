package kitchenpos.eatinorders.tobe.poc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
@Disabled
@DisplayName("주문 항목 메뉴 테스트")
class EatInOrderLineItemMenuTest {

    @DisplayName("주문 항목 메뉴의 식별자를 반환한다.")
    @Test
    void menuEatInOrderLineItemIdValue() {
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItemMenu eatInOrderLineItemMenu = new EatInOrderLineItemMenu(menuId, "후라이드치킨", 16_000);

        final UUID actual = eatInOrderLineItemMenu.menuId();

        assertThat(actual).isEqualTo(menuId);
    }
}
