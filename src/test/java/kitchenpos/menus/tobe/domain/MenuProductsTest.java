package kitchenpos.menus.tobe.domain;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.TobeFixtures.newMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuProductsTest {

    private final MenuProduct mp1 = newMenuProduct("상품1", 1000L);
    private final MenuProduct mp2 = newMenuProduct("상품2", 2000L);
    private final MenuProduct mp3 = newMenuProduct("상품3", 3000L);

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴 상품 목록이 비어있으면 MenuProducts 생성 실패")
    void createMenuProductsFail(List<MenuProduct> menuProducts) {
        assertThatIllegalArgumentException().isThrownBy(() -> MenuProducts.from(menuProducts));
    }

    @Test
    @DisplayName("메뉴 상품 생성 성공")
    void createMenuProductsSuccess() {
        // given
        List<MenuProduct> expected = Arrays.asList(mp1, mp2, mp3);

        // when
        MenuProducts actual = MenuProducts.from(expected);

        // then
        assertThat(actual.getItems()).containsExactly(mp1, mp2, mp3);
        assertThat(actual.getSumProductsPrice()).isEqualTo(mp1.getPrice() + mp2.getPrice() + mp3.getPrice());
    }
}
