package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.model.MenuProductModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @DisplayName("[정상] 메뉴 이름이 같으면 같은 객체이다.")
    @Test
    void valueObject_equals() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        MenuProducts menuProducts1 = new MenuProducts(List.of(
                new MenuProductModel(id1, new BigDecimal(10_000), 1),
                new MenuProductModel(id2, new BigDecimal(20_000), 2),
                new MenuProductModel(id3, new BigDecimal(30_000), 3)));
        MenuProducts menuProducts2 = new MenuProducts(List.of(
                new MenuProductModel(id1, new BigDecimal(10_000), 1),
                new MenuProductModel(id2, new BigDecimal(20_000), 2),
                new MenuProductModel(id3, new BigDecimal(30_000), 3)));

        assertThat(menuProducts1).isEqualTo(menuProducts2);
    }
}