package kitchenpos.eatinorders.tobe.domain.vo;

import static kitchenpos.eatinorders.tobe.OrderFixtures.menu;
import static kitchenpos.eatinorders.tobe.OrderFixtures.name;
import static kitchenpos.eatinorders.tobe.OrderFixtures.price;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DisplayedMenuTest {

    @DisplayName("전시 메뉴는 생성할 수 있다.")
    @Test
    void create() {
        assertThatCode(() -> menu("양념치킨", 16_000))
                .doesNotThrowAnyException();
    }

    @DisplayName("전시 메뉴는 반드시 식별자가 필요하다.")
    @Test
    void error() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new DisplayedMenu(
                                null,
                                name("양념치킨"),
                                price(16_000)
                        ));
    }
}
