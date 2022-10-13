package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class PriceTest {

    @Test
    void 메뉴의_가격은_0원_이상() {
        assertThrows(IllegalArgumentException.class, () -> new Price(-1));
    }
}