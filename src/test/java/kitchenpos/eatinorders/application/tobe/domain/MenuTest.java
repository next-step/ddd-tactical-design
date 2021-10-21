package kitchenpos.eatinorders.application.tobe.domain;

import kitchenpos.eatinorders.application.tobe.OrderFixtures;
import kitchenpos.eatinorders.tobe.domain.Menu;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class MenuTest extends OrderFixtures {

    @Test
    void 주문_메뉴_생성() {
        Menu menu = createMenu();

        assertThat(menu).isNotNull();
    }

    @Test
    void 주문_금액_확인() {
        Menu menu = createMenu();

        assertThat(menu.getMenuPrice()).isEqualTo(BigDecimal.TEN);
    }
}
