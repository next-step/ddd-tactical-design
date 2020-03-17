package kitchenpos.menus.tobe.domain.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static kitchenpos.menus.TobeFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @DisplayName("생성 되는지 테스트.")
    @Test
    void create() {
        MenuProducts result = new MenuProducts(Collections.singletonList(menuProduct()));

        assertThat(result).isNotNull();
        assertThat(result.getMenuProducts()).isEqualTo(Collections.singletonList(menuProduct()));
    }

    @DisplayName("ID 추출 테스트.")
    @Test
    void getMenuProductIds() {
        //given
        MenuProducts sample = new MenuProducts(Collections.singletonList(menuProduct()));

        // when
        List<Long> result = sample.getMenuProductIds();

        // then
        assertThat(result).isEqualTo(Collections.singletonList(1L));
    }
}