package kitchenpos.menus.tobe.domain;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupEmptyException;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {
    @Test
    void 메뉴는_특정_메뉴그룹에_속해야한다() {
        // given when & then
        assertThatThrownBy(() -> new Menu(null, "치킨 세트", 40000, true))
                .isInstanceOf(InvalidMenuGroupEmptyException.class)
                .hasMessage("메뉴는 반드시 특정 메뉴 그룹에 속해야 합니다.");
    }

